/**
 * Travel Japanese learning API.
 * NVIDIA_API_KEY from Vercel env / GitHub Secrets.
 * Curated phrase bank guarantees readable Hangul pronunciation;
 * NVIDIA is used when its output passes Japanese + Hangul checks.
 */
const MODEL = "meta/llama-3.2-11b-vision-instruct";
const NVIDIA_URL = "https://integrate.api.nvidia.com/v1/chat/completions";

const HAS_JAPANESE = /[\u3040-\u30ff\u3400-\u9fff]/;
const HAS_HANGUL = /[\uac00-\ud7a3]/;

/** Situation → verified travel phrases with correct Hangul readings */
const BANK = {
  식당: [
    {
      korean: "메뉴판 주세요.",
      japanese: "メニューをください。",
      pronunciation: "메뉴 오 쿠다사이",
      tip: "착석 후 메뉴를 받을 때 씁니다.",
    },
    {
      korean: "이것 주세요.",
      japanese: "これください。",
      pronunciation: "코레 쿠다사이",
      tip: "메뉴를 손가락으로 가리키며 주문할 때 유용합니다.",
    },
    {
      korean: "아이용 포크와 숟가락 부탁합니다.",
      japanese: "子供用のフォークとスプーンをお願いします。",
      pronunciation: "코도모요 노 포쿠 토 스푼 오 오네가이 시마스",
      tip: "젓가락이 불편한 아이에게 요청할 때.",
    },
    {
      korean: "와사비는 빼 주세요.",
      japanese: "わさび抜きでお願いします。",
      pronunciation: "와사비 누키 데 오네가이 시마스",
      tip: "아이들이 매운맛을 못 먹을 때.",
    },
    {
      korean: "계산해 주세요.",
      japanese: "お会計お願いします。",
      pronunciation: "오카이케이 오네가이 시마스",
      tip: "식사 후 계산을 요청할 때.",
    },
  ],
  교통: [
    {
      korean: "역은 어디인가요?",
      japanese: "駅はどこですか？",
      pronunciation: "에키 와 도코 데스카",
      tip: "길을 물을 때 가장 기본이 되는 표현.",
    },
    {
      korean: "신오사카까지 가고 싶어요.",
      japanese: "新大阪まで行きたいです。",
      pronunciation: "신오오사카 마데 이키타이 데스",
      tip: "택시·역무원에게 목적지를 말할 때.",
    },
    {
      korean: "이 열차는 고쿠라에 가나요?",
      japanese: "この電車は小倉に行きますか？",
      pronunciation: "코노 덴샤 와 코쿠라 니 이키마스카",
      tip: "승강장에서 행선을 확인할 때.",
    },
    {
      korean: "환승은 어디서 하나요?",
      japanese: "乗り換えはどこですか？",
      pronunciation: "노리카에 와 도코 데스카",
      tip: "지하철·JR 환승 위치를 물을 때.",
    },
    {
      korean: "택시를 타고 싶어요.",
      japanese: "タクシーに乗りたいです。",
      pronunciation: "타쿠시이 니 노리타이 데스",
      tip: "호텔 앞에서 택시를 요청할 때.",
    },
  ],
  호텔: [
    {
      korean: "체크인 하고 싶습니다.",
      japanese: "チェックインをお願いします。",
      pronunciation: "체쿠인 오 오네가이 시마스",
      tip: "프론트에서 체크인할 때.",
    },
    {
      korean: "짐을 맡아 주세요.",
      japanese: "荷物を預かってください。",
      pronunciation: "니모츠 오 아즈캇테 쿠다사이",
      tip: "체크인 전·체크아웃 후 짐 보관 요청.",
    },
    {
      korean: "와이파이 비밀번호가 뭔가요?",
      japanese: "Wi-Fiのパスワードは何ですか？",
      pronunciation: "와이파이 노 파스와아도 와 난 데스카",
      tip: "객실 와이파이 연결 시.",
    },
    {
      korean: "수건을 더 주세요.",
      japanese: "タオルを追加してください。",
      pronunciation: "타오루 오 츠이카 시테 쿠다사이",
      tip: "어메니티 추가 요청.",
    },
  ],
  쇼핑: [
    {
      korean: "이거 얼마예요?",
      japanese: "これはいくらですか？",
      pronunciation: "코레 와 이쿠라 데스카",
      tip: "가격을 물을 때.",
    },
    {
      korean: "면세 되나요?",
      japanese: "免税できますか？",
      pronunciation: "멘제이 데키마스카",
      tip: "돈키호테·약국 등에서.",
    },
    {
      korean: "봉지에 넣어 주세요.",
      japanese: "袋に入れてください。",
      pronunciation: "후쿠로 니 이레테 쿠다사이",
      tip: "계산 후 포장 요청.",
    },
    {
      korean: "카드로 결제할게요.",
      japanese: "カードで払います。",
      pronunciation: "카아도 데 하라이마스",
      tip: "현금 대신 카드 결제할 때.",
    },
  ],
  아이동반: [
    {
      korean: "아이가 있습니다.",
      japanese: "子供がいます。",
      pronunciation: "코도모 가 이마스",
      tip: "식당·호텔에 아이 동반을 알릴 때.",
    },
    {
      korean: "어린이용 메뉴 있나요?",
      japanese: "子供向けのメニューはありますか？",
      pronunciation: "코도모 무케 노 메뉴 와 아리마스카",
      tip: "패밀리 레스토랑에서.",
    },
    {
      korean: "화장실은 어디인가요?",
      japanese: "トイレはどこですか？",
      pronunciation: "토이레 와 도코 데스카",
      tip: "아이와 함께일 때 자주 씁니다.",
    },
    {
      korean: "물이 마시고 싶대요.",
      japanese: "水が欲しいです。",
      pronunciation: "미즈 가 호시이 데스",
      tip: "편의점·식당에서.",
    },
  ],
  응급: [
    {
      korean: "도움이 필요합니다.",
      japanese: "助けてください。",
      pronunciation: "타스케테 쿠다사이",
      tip: "긴급 상황에서 주변에 요청.",
    },
    {
      korean: "병원에 가고 싶어요.",
      japanese: "病院に行きたいです。",
      pronunciation: "뵤오인 니 이키타이 데스",
      tip: "호텔 프론트·경찰에 요청.",
    },
    {
      korean: "약을 사고 싶어요.",
      japanese: "薬を買いたいです。",
      pronunciation: "쿠스리 오 카이타이 데스",
      tip: "드럭스토어에서.",
    },
    {
      korean: "길을 잃었어요.",
      japanese: "道に迷いました。",
      pronunciation: "미치 니 마요이마시타",
      tip: "현지인에게 위치를 물을 때.",
    },
  ],
};

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

function isJapaneseText(s) {
  const t = String(s || "");
  return HAS_JAPANESE.test(t) && !HAS_HANGUL.test(t);
}

function isHangulPronunciation(s) {
  const t = String(s || "").trim();
  return t.length > 0 && HAS_HANGUL.test(t) && !HAS_JAPANESE.test(t);
}

function pickBank(situation, count) {
  const list = BANK[situation] || BANK["교통"];
  const shuffled = [...list].sort(() => Math.random() - 0.5);
  return shuffled.slice(0, count).map((p, i) => ({
    id: i + 1,
    situation,
    ...p,
  }));
}

function bankQuiz(situation) {
  const list = BANK[situation] || BANK["교통"];
  const correct = list[Math.floor(Math.random() * list.length)];
  const wrongs = list.filter((p) => p.japanese !== correct.japanese).slice(0, 3);
  while (wrongs.length < 3) {
    const otherSit = Object.keys(BANK).find((k) => k !== situation) || "식당";
    const alt = BANK[otherSit][wrongs.length % BANK[otherSit].length];
    if (!wrongs.find((w) => w.japanese === alt.japanese) && alt.japanese !== correct.japanese) {
      wrongs.push(alt);
    } else break;
  }
  const choices = [correct.japanese, ...wrongs.map((w) => w.japanese)].slice(0, 4);
  for (let i = choices.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [choices[i], choices[j]] = [choices[j], choices[i]];
  }
  return {
    promptKorean: `「${correct.korean}」의 일본어는?`,
    correctJapanese: correct.japanese,
    pronunciation: correct.pronunciation,
    choices,
    explanation: `정답은 「${correct.japanese}」입니다. 한글 발음: ${correct.pronunciation}`,
  };
}

async function callNvidia(messages, max_tokens = 900) {
  const key = process.env.NVIDIA_API_KEY;
  if (!key) return null;
  try {
    const r = await fetch(NVIDIA_URL, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${key}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        model: MODEL,
        messages,
        temperature: 0.2,
        max_tokens,
        stream: false,
      }),
    });
    const data = await r.json().catch(() => ({}));
    if (!r.ok) return null;
    return data.choices?.[0]?.message?.content || null;
  } catch (_) {
    return null;
  }
}

function filterPhrases(phrases, situation) {
  return (phrases || [])
    .filter(
      (p) =>
        p &&
        isJapaneseText(p.japanese) &&
        isHangulPronunciation(p.pronunciation) &&
        HAS_HANGUL.test(String(p.korean || ""))
    )
    .map((p, i) => ({
      id: i + 1,
      situation: p.situation || situation,
      korean: p.korean,
      japanese: p.japanese,
      pronunciation: String(p.pronunciation).trim(),
      tip: p.tip || "",
    }));
}

function systemPrompt() {
  return `Japanese travel tutor for Koreans. Output ONLY JSON.
japanese fields = Japanese script ONLY (no Hangul).
pronunciation = Korean Hangul reading with spaces that Koreans can read aloud.
Standard readings: ください=쿠다사이, です=데스, ですか=데스카, タクシー=타쿠시이, お願いします=오네가이 시마스.`;
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
      // Always serve curated phrases so Hangul readings stay correct.
      const phrases = pickBank(situation, count);
      return res.status(200).json({ phrases, source: "bank" });
    }

    if (action === "quiz") {
      // Always use bank for quiz reliability (Japanese choices + correct Hangul)
      const quiz = bankQuiz(situation);
      return res.status(200).json({ quiz });
    }

    if (action === "coach") {
      const userText = String(body.userText || "").trim();
      if (!userText) return res.status(400).json({ error: "userText required" });

      // Try match bank first for common phrases
      const all = Object.entries(BANK).flatMap(([sit, list]) => list.map((p) => ({ ...p, situation: sit })));
      const hit = all.find(
        (p) =>
          userText.includes(p.korean.replace(/[.?！？。]/g, "")) ||
          userText.includes(p.japanese) ||
          p.korean.includes(userText)
      );
      if (hit) {
        return res.status(200).json({
          result: {
            correctedJapanese: hit.japanese,
            pronunciation: hit.pronunciation,
            naturalKorean: hit.korean,
            feedback: "현장에서 바로 쓰기 좋은 표현입니다.",
            betterAlternatives: [],
          },
        });
      }

      const ai = await callNvidia([
        { role: "system", content: systemPrompt() },
        {
          role: "user",
          content: `Fix for travel Japanese. Input: """${userText}""" Situation:${situation}
JSON: correctedJapanese, pronunciation (Hangul with spaces), naturalKorean, feedback, betterAlternatives (max 2 Japanese strings)`,
        },
      ]);
      const result = ai ? extractJson(ai) : null;
      if (result && isJapaneseText(result.correctedJapanese) && isHangulPronunciation(result.pronunciation)) {
        result.betterAlternatives = (result.betterAlternatives || []).filter(isJapaneseText);
        return res.status(200).json({ result });
      }

      // Fallback nearest bank item for situation
      const fallback = (BANK[situation] || BANK["교통"])[0];
      return res.status(200).json({
        result: {
          correctedJapanese: fallback.japanese,
          pronunciation: fallback.pronunciation,
          naturalKorean: fallback.korean,
          feedback: "입력과 가까운 추천 문장입니다. 한글 발음을 따라 읽어 보세요.",
          betterAlternatives: [],
        },
      });
    }

    return res.status(400).json({ error: "Unknown action" });
  } catch (e) {
    return res.status(e.status || 500).json({ error: e.message || "Server error" });
  }
};
