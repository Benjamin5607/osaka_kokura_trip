/**
 * Travel Japanese learning proxy → NVIDIA NIM (OpenAI-compatible).
 * Reads NVIDIA_API_KEY from Vercel / GitHub Actions secrets (never from the client).
 */
const MODEL = "meta/llama-3.2-11b-vision-instruct";
const NVIDIA_URL = "https://integrate.api.nvidia.com/v1/chat/completions";

function cors(res) {
  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
  res.setHeader("Access-Control-Allow-Headers", "Content-Type");
}

function extractJson(text) {
  const raw = String(text || "").trim();
  try {
    return JSON.parse(raw);
  } catch (_) {}
  const m = raw.match(/\{[\s\S]*\}|\[[\s\S]*\]/);
  if (m) {
    try {
      return JSON.parse(m[0]);
    } catch (_) {}
  }
  return null;
}

async function callNvidia(messages, max_tokens = 900) {
  const key = process.env.NVIDIA_API_KEY;
  if (!key) {
    const err = new Error("NVIDIA_API_KEY is not configured");
    err.status = 500;
    throw err;
  }
  const r = await fetch(NVIDIA_URL, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${key}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      model: MODEL,
      messages,
      temperature: 0.45,
      max_tokens,
      stream: false,
    }),
  });
  const data = await r.json().catch(() => ({}));
  if (!r.ok) {
    const err = new Error(data.detail || data.title || `NVIDIA API ${r.status}`);
    err.status = r.status;
    throw err;
  }
  return data.choices?.[0]?.message?.content || "";
}

function systemPrompt() {
  return `You are a Japanese travel conversation tutor for a Korean family visiting Osaka, Kyoto, and Kitakyushu (Kokura/Mojiko) with elementary-school kids.
Rules:
- Prefer polite, practical spoken Japanese useful on-site.
- Avoid raw fish / organ meat menu advice; kids-friendly.
- Always respond with ONLY valid JSON (no markdown fences).
- Korean explanations should be clear and short.`;
}

module.exports = async function handler(req, res) {
  cors(res);
  if (req.method === "OPTIONS") return res.status(204).end();
  if (req.method !== "POST") return res.status(405).json({ error: "POST only" });

  try {
    const body = typeof req.body === "string" ? JSON.parse(req.body || "{}") : req.body || {};
    const action = body.action || "generate";
    const situation = body.situation || "식당";
    const level = body.level || "초급";
    const count = Math.min(Number(body.count) || 4, 6);

    if (action === "generate") {
      const content = await callNvidia([
        { role: "system", content: systemPrompt() },
        {
          role: "user",
          content: `상황: ${situation}\n수준: ${level}\n여행용 일본어 문장 ${count}개를 JSON 배열로 만들어라.
각 항목 키: id(number), situation(string), korean(string), japanese(string), pronunciation(string, 한글 발음), tip(string, 사용 팁 한국어).`,
        },
      ]);
      const parsed = extractJson(content);
      const phrases = Array.isArray(parsed) ? parsed : parsed?.phrases || [];
      return res.status(200).json({ phrases });
    }

    if (action === "quiz") {
      const content = await callNvidia([
        { role: "system", content: systemPrompt() },
        {
          role: "user",
          content: `상황: ${situation}\n수준: ${level}\n퀴즈 1문항을 JSON 객체로 만들어라.
키: promptKorean(한국어 질문), correctJapanese(정답 일본어), pronunciation(한글 발음), choices(일본어 보기 문자열 배열 4개, 정답 포함 셔플), explanation(한국어 해설).`,
        },
      ]);
      const quiz = extractJson(content);
      return res.status(200).json({ quiz });
    }

    if (action === "coach") {
      const userText = String(body.userText || "").trim();
      if (!userText) return res.status(400).json({ error: "userText required" });
      const content = await callNvidia([
        { role: "system", content: systemPrompt() },
        {
          role: "user",
          content: `학습자가 말한/쓴 내용: """${userText}"""
상황: ${situation}
JSON 객체로 답하라. 키: correctedJapanese, pronunciation, naturalKorean, feedback(한국어, 짧게), betterAlternatives(일본어 문장 배열 최대 2개).`,
        },
      ]);
      const result = extractJson(content);
      return res.status(200).json({ result });
    }

    return res.status(400).json({ error: "Unknown action" });
  } catch (e) {
    return res.status(e.status || 500).json({ error: e.message || "Server error" });
  }
};
