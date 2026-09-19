const STORAGE_KEY = "osaka-kokura-trip-v1";

const state = {
  tab: "schedule",
  day: 1,
  category: "전체",
  search: "",
  gourmetCity: "전체",
  gourmetCat: "전체",
  sosSub: 0,
  phraseCat: "전체",
  data: null,
  completed: {},
  checked: {},
  flash: null,
  jaMode: "generate", // generate | quiz | coach
  jaSituation: "식당",
  jaLevel: "초급",
  jaPhrases: [],
  jaQuiz: null,
  jaQuizPicked: null,
  jaCoachText: "",
  jaCoachResult: null,
  jaLoading: false,
  jaError: "",
};

const CAT_ORDER = ["전체", "교통", "관광", "맛집", "쇼핑", "온천", "숙소"];

function loadPersist() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return;
    const p = JSON.parse(raw);
    state.completed = p.completed || {};
    state.checked = p.checked || {};
  } catch (_) {}
}

function savePersist() {
  localStorage.setItem(
    STORAGE_KEY,
    JSON.stringify({ completed: state.completed, checked: state.checked })
  );
}

function esc(s) {
  return String(s ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");
}

function speak(text) {
  if (!window.speechSynthesis) return;
  window.speechSynthesis.cancel();
  const u = new SpeechSynthesisUtterance(text);
  u.lang = "ja-JP";
  u.rate = 0.9;
  window.speechSynthesis.speak(u);
}

async function boot() {
  loadPersist();
  const res = await fetch("./data.json");
  state.data = await res.json();

  // hydrate checklist defaults into checked map once
  if (Object.keys(state.checked).length === 0) {
    for (const c of state.data.checklists) {
      state.checked[c.id] = !!c.isChecked;
    }
    savePersist();
  }

  document.querySelectorAll(".nav-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
      state.tab = btn.dataset.tab;
      document.querySelectorAll(".nav-btn").forEach((b) => {
        b.classList.toggle("active", b === btn);
        b.toggleAttribute("aria-current", b === btn ? "page" : false);
      });
      render();
      window.scrollTo({ top: 0, behavior: "smooth" });
    });
  });

  render();
}

function render() {
  const main = document.getElementById("main");
  const map = {
    schedule: renderSchedule,
    transit: renderTransit,
    gourmet: renderGourmet,
    japanese: renderJapanese,
    sos: renderSos,
    checklist: renderChecklist,
  };
  main.innerHTML = map[state.tab]();
  bindMain();
  renderModal();
}

function bindMain() {
  const main = document.getElementById("main");

  main.querySelectorAll("[data-day]").forEach((el) => {
    el.addEventListener("click", () => {
      state.day = Number(el.dataset.day);
      render();
    });
  });
  main.querySelectorAll("[data-cat]").forEach((el) => {
    el.addEventListener("click", () => {
      state.category = el.dataset.cat;
      render();
    });
  });
  const search = main.querySelector("#search");
  if (search) {
    search.addEventListener("input", (e) => {
      state.search = e.target.value;
      // soft re-render schedule list only
      const list = main.querySelector("#schedule-list");
      if (list) list.innerHTML = scheduleCardsHtml();
      bindScheduleCards();
    });
  }
  bindScheduleCards();

  main.querySelectorAll("[data-gcity]").forEach((el) => {
    el.addEventListener("click", () => {
      state.gourmetCity = el.dataset.gcity;
      render();
    });
  });
  main.querySelectorAll("[data-gcat]").forEach((el) => {
    el.addEventListener("click", () => {
      state.gourmetCat = el.dataset.gcat;
      render();
    });
  });

  main.querySelectorAll("[data-sos]").forEach((el) => {
    el.addEventListener("click", () => {
      state.sosSub = Number(el.dataset.sos);
      render();
    });
  });
  main.querySelectorAll("[data-pcat]").forEach((el) => {
    el.addEventListener("click", () => {
      state.phraseCat = el.dataset.pcat;
      render();
    });
  });
  main.querySelectorAll("[data-phrase]").forEach((el) => {
    el.addEventListener("click", () => {
      const id = Number(el.dataset.phrase);
      state.flash = state.data.phrases.find((p) => p.id === id) || null;
      renderModal();
    });
  });

  main.querySelectorAll("[data-check]").forEach((el) => {
    el.addEventListener("change", () => {
      const id = el.dataset.check;
      state.checked[id] = el.checked;
      savePersist();
      el.closest(".check-item")?.classList.toggle("checked", el.checked);
    });
  });

  // Japanese learning controls
  main.querySelectorAll("[data-jamode]").forEach((el) => {
    el.addEventListener("click", () => {
      state.jaMode = el.dataset.jamode;
      state.jaError = "";
      state.jaQuizPicked = null;
      render();
    });
  });
  main.querySelectorAll("[data-jasit]").forEach((el) => {
    el.addEventListener("click", () => {
      state.jaSituation = el.dataset.jasit;
      render();
    });
  });
  main.querySelectorAll("[data-jalevel]").forEach((el) => {
    el.addEventListener("click", () => {
      state.jaLevel = el.dataset.jalevel;
      render();
    });
  });
  const genBtn = main.querySelector("#ja-generate");
  if (genBtn) genBtn.addEventListener("click", () => generateJapanesePhrases());
  const quizBtn = main.querySelector("#ja-quiz");
  if (quizBtn) quizBtn.addEventListener("click", () => generateJapaneseQuiz());
  main.querySelectorAll("[data-quiz-choice]").forEach((el) => {
    el.addEventListener("click", () => {
      if (state.jaQuizPicked != null) return;
      state.jaQuizPicked = el.dataset.quizChoice;
      render();
    });
  });
  const coachInput = main.querySelector("#ja-coach-input");
  if (coachInput) {
    coachInput.addEventListener("input", (e) => {
      state.jaCoachText = e.target.value;
    });
  }
  const coachBtn = main.querySelector("#ja-coach");
  if (coachBtn) coachBtn.addEventListener("click", () => coachJapanese());
  main.querySelectorAll("[data-speak-ja]").forEach((el) => {
    el.addEventListener("click", () => speak(el.dataset.speakJa));
  });

}

function bindScheduleCards() {
  const main = document.getElementById("main");
  main.querySelectorAll("[data-toggle]").forEach((el) => {
    el.addEventListener("click", () => {
      const id = el.dataset.toggle;
      state.completed[id] = !state.completed[id];
      savePersist();
      render();
    });
  });
}

function filteredSchedules() {
  const q = state.search.trim().toLowerCase();
  return state.data.schedules.filter((s) => {
    if (s.dayNumber !== state.day) return false;
    if (state.category !== "전체" && s.category !== state.category) return false;
    if (!q) return true;
    const hay = [s.title, s.locationName, s.transitGuide, s.restaurantName, s.kidsFriendlyTip, s.description]
      .join(" ")
      .toLowerCase();
    return hay.includes(q);
  });
}

function scheduleCardsHtml() {
  const items = filteredSchedules();
  if (!items.length) return `<p class="empty">해당 조건의 일정이 없습니다.</p>`;
  return items
    .map((s, i) => {
      const done = !!state.completed[s.id];
      return `
      <article class="card ${done ? "done" : ""}" style="animation-delay:${i * 40}ms">
        <div class="card-top">
          <span class="time">${esc(s.timeSlot)}</span>
          <span class="badge ${esc(s.category)}">${esc(s.category)}</span>
        </div>
        <h3>${esc(s.title)}</h3>
        <p class="meta">${esc(s.city)} · ${esc(s.locationName)}</p>
        <p class="muted">${esc(s.description)}</p>
        <p class="muted" style="margin-top:8px"><strong>이동:</strong> ${esc(s.transitGuide)}</p>
        ${s.kidsFriendlyTip ? `<div class="tip"><strong>아이 팁</strong><br>${esc(s.kidsFriendlyTip)}</div>` : ""}
        ${
          s.restaurantName
            ? `<div class="safe"><strong>${esc(s.restaurantName)}</strong><br>${esc(s.restaurantMenu)}<br>${esc(s.restaurantFeature)}</div>`
            : ""
        }
        <div class="actions">
          <button type="button" class="btn ${done ? "btn-ghost" : "btn-primary"}" data-toggle="${s.id}">
            ${done ? "완료 취소" : "일정 완료"}
          </button>
        </div>
      </article>`;
    })
    .join("");
}

function renderSchedule() {
  const dayMeta = state.data.meta.days.find((d) => d.day === state.day);
  return `
    <h2 class="panel-title">일정표</h2>
    <p class="panel-desc">${esc(dayMeta?.date || "")} · ${esc(dayMeta?.city || "")}</p>
    <div class="chip-row">
      ${state.data.meta.days
        .map(
          (d) =>
            `<button type="button" class="chip ${state.day === d.day ? "active" : ""}" data-day="${d.day}">${esc(d.label)} ${esc(d.date)}</button>`
        )
        .join("")}
    </div>
    <div class="chip-row">
      ${CAT_ORDER.map(
        (c) =>
          `<button type="button" class="chip ${state.category === c ? "active" : ""}" data-cat="${esc(c)}">${esc(c)}</button>`
      ).join("")}
    </div>
    <input id="search" class="search" type="search" placeholder="장소, 맛집, 이동수단 검색…" value="${esc(state.search)}" />
    <div id="schedule-list">${scheduleCardsHtml()}</div>
  `;
}

function renderTransit() {
  const t = state.data.transit;
  return `
    <div class="hero-banner">
      <h2>${esc(t.header.title)}</h2>
      <p>${esc(t.header.description)}</p>
    </div>
    ${t.sections
      .map(
        (s, i) => `
      <article class="card" style="animation-delay:${i * 50}ms">
        <div class="card-top">
          <span class="badge" style="background:${esc(s.badgeColor)};color:#fff">${esc(s.badgeText)}</span>
        </div>
        <h3>${esc(s.title)}</h3>
        <p class="meta">${esc(s.routeSummary)}</p>
        <ol class="step-list">${s.steps.map((st) => `<li>${esc(st)}</li>`).join("")}</ol>
        <div class="tip">${esc(s.tip)}</div>
      </article>`
      )
      .join("")}
    <article class="card">
      <h3>${esc(t.kitakyushuPass.title)}</h3>
      <div class="pass-grid" style="margin-top:12px">
        ${t.kitakyushuPass.benefits
          .map(
            (b) => `
          <div class="pass-item">
            <strong>${esc(b.title)}</strong>
            <span class="muted">${esc(b.description)}</span>
          </div>`
          )
          .join("")}
      </div>
    </article>
  `;
}

function renderGourmet() {
  const cities = ["전체", "오사카", "키타큐슈", "교토"];
  const cats = ["전체", "돈카츠", "우동", "함바그·양식", "야키카레", "오코노미야키", "샌드위치", "베이커리", "라멘", "쿠시카츠"];
  const items = state.data.gourmet.filter((g) => {
    if (state.gourmetCity !== "전체" && g.city !== state.gourmetCity) return false;
    if (state.gourmetCat !== "전체" && g.category !== state.gourmetCat) return false;
    return true;
  });
  return `
    <div class="hero-banner">
      <h2>100% 안심 보증: 회 &amp; 내장 요리 완전 제외</h2>
      <p>초등학생 아이들이 환호하는 일본인들의 현지 찐 맛집</p>
    </div>
    <div class="chip-row">
      ${cities
        .map(
          (c) =>
            `<button type="button" class="chip ${state.gourmetCity === c ? "active" : ""}" data-gcity="${esc(c)}">${esc(c)}</button>`
        )
        .join("")}
    </div>
    <div class="chip-row">
      ${cats
        .map(
          (c) =>
            `<button type="button" class="chip ${state.gourmetCat === c ? "active" : ""}" data-gcat="${esc(c)}">${esc(c)}</button>`
        )
        .join("")}
    </div>
    ${
      items.length
        ? items
            .map(
              (g, i) => `
      <article class="card" style="animation-delay:${i * 40}ms">
        <div class="card-top">
          <span class="badge 맛집">${esc(g.category)}</span>
          <span class="meta" style="margin:0">${esc(g.city)}</span>
        </div>
        <h3>${esc(g.nameKo)}</h3>
        <p class="meta">${esc(g.nameJa)} · ${esc(g.area)}</p>
        <p class="muted"><strong>아이 메뉴:</strong> ${esc(g.kidMenu)}</p>
        <p class="muted"><strong>가격:</strong> ${esc(g.price)}</p>
        <p class="muted" style="margin-top:8px">${esc(g.localFeature)}</p>
        <div class="tip">${esc(g.kidComfortTip)}</div>
        <div class="actions">
          <a class="btn btn-ghost" target="_blank" rel="noopener" href="https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(g.addressQuery || g.nameJa || g.nameKo)}">지도에서 찾기</a>
        </div>
      </article>`
            )
            .join("")
        : `<p class="empty">조건에 맞는 맛집이 없습니다.</p>`
    }
  `;
}

function renderSos() {
  const tabs = ["대체 실내코스", "실시간 정보", "비상연락망", "비상 일본어"];
  let body = "";
  if (state.sosSub === 0) {
    body = state.data.alternatives
      .map(
        (a, i) => `
      <article class="card" style="animation-delay:${i * 40}ms">
        <div class="card-top">
          <span class="badge" style="background:#fee2e2;color:#b91c1c">Day ${a.dayNumber}</span>
          <span class="meta" style="margin:0">${esc(a.situation)}</span>
        </div>
        <h3>${esc(a.alternativeTitle)}</h3>
        <p class="meta">원래: ${esc(a.originalSpot)}</p>
        <p class="muted">${esc(a.description)}</p>
        <p class="muted" style="margin-top:8px"><strong>이동:</strong> ${esc(a.transitGuide)}</p>
        <div class="safe">${esc(a.indoorAdvantage)}</div>
        <div class="tip">${esc(a.nearbyFoodTip)}</div>
      </article>`
      )
      .join("");
  } else if (state.sosSub === 1) {
    body =
      `<p class="section-label">실시간 교통</p>` +
      state.data.trafficLinks
        .map(
          (t) => `
        <a class="card link-card" href="${esc(t.url)}" target="_blank" rel="noopener">
          <h3>${esc(t.title)}</h3>
          <p class="meta">${esc(t.lineName)}</p>
          <p class="muted">${esc(t.description)}</p>
          <div class="tip">${esc(t.delayTip)}</div>
        </a>`
        )
        .join("") +
      `<p class="section-label">날씨 가이드</p>` +
      state.data.weatherGuides
        .map(
          (w) => `
        <a class="card link-card" href="${esc(w.officialUrl)}" target="_blank" rel="noopener">
          <h3>${esc(w.cityName)}</h3>
          <p class="meta">${esc(w.region)} · ${esc(w.avgTemp)}</p>
          <p class="muted">${esc(w.weatherTips)}</p>
          <div class="safe">${esc(w.rainyAlternativeSummary)}</div>
        </a>`
        )
        .join("");
  } else if (state.sosSub === 2) {
    const grouped = {};
    for (const c of state.data.contacts) {
      (grouped[c.category] ||= []).push(c);
    }
    body = Object.entries(grouped)
      .map(
        ([cat, list]) => `
      <p class="section-label">${esc(cat)}</p>
      ${list
        .map(
          (c) => `
        <article class="card">
          <h3>${esc(c.name)}</h3>
          <p class="meta"><a href="tel:${esc(c.phoneNumber)}">${esc(c.phoneNumber)}</a>${c.emergencyPhone ? ` · 비상 ${esc(c.emergencyPhone)}` : ""}</p>
          ${c.address ? `<p class="muted">${esc(c.address)}</p>` : ""}
          ${c.note ? `<div class="tip">${esc(c.note)}</div>` : ""}
        </article>`
        )
        .join("")}`
      )
      .join("");
  } else {
    const cats = ["전체", ...new Set(state.data.phrases.map((p) => p.category))];
    const list = state.data.phrases.filter(
      (p) => state.phraseCat === "전체" || p.category === state.phraseCat
    );
    body =
      `<div class="chip-row">${cats
        .map(
          (c) =>
            `<button type="button" class="chip ${state.phraseCat === c ? "active" : ""}" data-pcat="${esc(c)}">${esc(c)}</button>`
        )
        .join("")}</div>` +
      list
        .map(
          (p) => `
        <article class="card phrase-card" data-phrase="${p.id}">
          <p class="meta">${esc(p.category)}</p>
          <h3>${esc(p.koreanTitle)}</h3>
          <p class="ja">${esc(p.japaneseText)}</p>
          <p class="muted">${esc(p.pronunciation)}</p>
        </article>`
        )
        .join("");
  }

  return `
    <h2 class="panel-title">돌발·SOS</h2>
    <p class="panel-desc">우천 대체 코스, 실시간 교통·날씨, 비상연락망, 일본어 회화</p>
    <div class="subtabs">
      ${tabs
        .map(
          (t, i) =>
            `<button type="button" class="subtab ${state.sosSub === i ? "active" : ""}" data-sos="${i}">${esc(t)}</button>`
        )
        .join("")}
    </div>
    ${body}
  `;
}

function renderChecklist() {
  const grouped = {};
  for (const c of state.data.checklists) {
    (grouped[c.category] ||= []).push(c);
  }
  const total = state.data.checklists.length;
  const done = state.data.checklists.filter((c) => state.checked[c.id]).length;
  return `
    <h2 class="panel-title">체크리스트</h2>
    <p class="panel-desc">준비 ${done}/${total} 완료 · 상태는 이 기기에 저장됩니다</p>
    ${Object.entries(grouped)
      .map(
        ([cat, list]) => `
      <p class="section-label">${esc(cat)}</p>
      ${list
        .map((c) => {
          const on = !!state.checked[c.id];
          return `
          <label class="check-item ${on ? "checked" : ""}">
            <input type="checkbox" data-check="${c.id}" ${on ? "checked" : ""} />
            <span>${esc(c.title)}</span>
          </label>`;
        })
        .join("")}`
      )
      .join("")}
  `;
}


const JA_SITUATIONS = ["식당", "교통", "호텔", "쇼핑", "아이동반", "응급"];
const JA_LEVELS = ["초급", "중급"];

async function callJapaneseApi(payload) {
  const res = await fetch("/api/japanese", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data.error || `API ${res.status}`);
  return data;
}

async function generateJapanesePhrases() {
  state.jaLoading = true;
  state.jaError = "";
  state.jaPhrases = [];
  render();
  try {
    const data = await callJapaneseApi({
      action: "generate",
      situation: state.jaSituation,
      level: state.jaLevel,
      count: 4,
    });
    state.jaPhrases = data.phrases || [];
    if (!state.jaPhrases.length) state.jaError = "문장을 생성하지 못했습니다. 다시 시도해 주세요.";
  } catch (e) {
    state.jaError = e.message || "생성 실패";
  } finally {
    state.jaLoading = false;
    render();
  }
}

async function generateJapaneseQuiz() {
  state.jaLoading = true;
  state.jaError = "";
  state.jaQuiz = null;
  state.jaQuizPicked = null;
  render();
  try {
    const data = await callJapaneseApi({
      action: "quiz",
      situation: state.jaSituation,
      level: state.jaLevel,
    });
    state.jaQuiz = data.quiz;
    if (!state.jaQuiz) state.jaError = "퀴즈를 만들지 못했습니다.";
  } catch (e) {
    state.jaError = e.message || "퀴즈 실패";
  } finally {
    state.jaLoading = false;
    render();
  }
}

async function coachJapanese() {
  if (!state.jaCoachText.trim()) {
    state.jaError = "교정할 문장이나 한국어 표현을 입력해 주세요.";
    render();
    return;
  }
  state.jaLoading = true;
  state.jaError = "";
  state.jaCoachResult = null;
  render();
  try {
    const data = await callJapaneseApi({
      action: "coach",
      situation: state.jaSituation,
      userText: state.jaCoachText,
    });
    state.jaCoachResult = data.result;
  } catch (e) {
    state.jaError = e.message || "코칭 실패";
  } finally {
    state.jaLoading = false;
    render();
  }
}

function renderJapanese() {
  const modes = [
    ["generate", "문장 생성"],
    ["quiz", "퀴즈"],
    ["coach", "교정 코칭"],
  ];
  let body = "";
  if (state.jaLoading) {
    body = `<p class="loading-line">NVIDIA AI가 여행 일본어를 준비 중…</p>`;
  } else if (state.jaMode === "generate") {
    body = `
      <button type="button" class="btn btn-primary" id="ja-generate">이 상황 문장 4개 만들기</button>
      ${state.jaError ? `<div class="tip" style="margin-top:12px">${esc(state.jaError)}</div>` : ""}
      ${(state.jaPhrases || [])
        .map(
          (p, i) => `
        <article class="card" style="animation-delay:${i * 40}ms">
          <p class="meta">${esc(p.situation || state.jaSituation)}</p>
          <h3>${esc(p.korean || "")}</h3>
          <p class="ja-big">${esc(p.japanese || "")}</p>
          <p class="muted"><strong>한글 발음:</strong> ${esc(p.pronunciation || "")}</p>
          ${p.tip ? `<div class="tip">${esc(p.tip)}</div>` : ""}
          <div class="actions">
            <button type="button" class="btn btn-ghost" data-speak-ja="${esc(p.japanese || "")}">일본어 읽기</button>
          </div>
        </article>`
        )
        .join("")}`;
  } else if (state.jaMode === "quiz") {
    const q = state.jaQuiz;
    body = `
      <button type="button" class="btn btn-primary" id="ja-quiz">새 퀴즈 받기</button>
      ${state.jaError ? `<div class="tip" style="margin-top:12px">${esc(state.jaError)}</div>` : ""}
      ${
        q
          ? `<article class="card">
        <p class="meta">알맞은 일본어를 고르세요</p>
        <h3>${esc(q.promptKorean || "")}</h3>
        ${(q.choices || [])
          .map((c) => {
            let cls = "quiz-choice";
            if (state.jaQuizPicked != null) {
              if (c === q.correctJapanese) cls += " correct";
              else if (c === state.jaQuizPicked) cls += " wrong";
            }
            return `<button type="button" class="${cls}" data-quiz-choice="${esc(c)}">${esc(c)}</button>`;
          })
          .join("")}
        ${
          state.jaQuizPicked != null
            ? `<div class="safe" style="margin-top:10px"><strong>한글 발음:</strong> ${esc(q.pronunciation || "")}<br>${esc(q.explanation || "")}</div>
               <div class="actions"><button type="button" class="btn btn-ghost" data-speak-ja="${esc(q.correctJapanese || "")}">정답 읽기</button></div>`
            : ""
        }
      </article>`
          : ""
      }`;
  } else {
    const r = state.jaCoachResult;
    body = `
      <textarea id="ja-coach-input" class="coach-box" placeholder="한국어로 하고 싶은 말, 또는 연습한 일본어를 적어 보세요">${esc(state.jaCoachText)}</textarea>
      <div class="actions" style="margin-top:10px">
        <button type="button" class="btn btn-primary" id="ja-coach">교정 받기</button>
      </div>
      ${state.jaError ? `<div class="tip" style="margin-top:12px">${esc(state.jaError)}</div>` : ""}
      ${
        r
          ? `<article class="card">
        <p class="meta">자연스러운 일본어</p>
        <p class="ja-big">${esc(r.correctedJapanese || "")}</p>
        <p class="muted"><strong>한글 발음:</strong> ${esc(r.pronunciation || "")}</p>
        <p class="muted"><strong>의미:</strong> ${esc(r.naturalKorean || "")}</p>
        <div class="tip">${esc(r.feedback || "")}</div>
        ${(r.betterAlternatives || []).map((a) => `<p class="muted" style="margin-top:8px">• ${esc(a)}</p>`).join("")}
        <div class="actions">
          <button type="button" class="btn btn-ghost" data-speak-ja="${esc(r.correctedJapanese || "")}">일본어 읽기</button>
        </div>
      </article>`
          : ""
      }`;
  }

  return `
    <div class="hero-banner">
      <h2>여행 일본어 학습 코너</h2>
      <p>NVIDIA AI로 오사카·키타큐슈 현장에서 바로 쓰는 문장을 연습하세요.</p>
    </div>
    <div class="ja-mode-tabs">
      ${modes
        .map(
          ([id, label]) =>
            `<button type="button" class="subtab ${state.jaMode === id ? "active" : ""}" data-jamode="${id}">${label}</button>`
        )
        .join("")}
    </div>
    <div class="chip-row">
      ${JA_SITUATIONS.map(
        (s) =>
          `<button type="button" class="chip ${state.jaSituation === s ? "active" : ""}" data-jasit="${esc(s)}">${esc(s)}</button>`
      ).join("")}
    </div>
    <div class="chip-row">
      ${JA_LEVELS.map(
        (l) =>
          `<button type="button" class="chip ${state.jaLevel === l ? "active" : ""}" data-jalevel="${esc(l)}">${esc(l)}</button>`
      ).join("")}
    </div>
    ${body}
  `;
}


function renderModal() {
  const root = document.getElementById("modal-root");
  if (!state.flash) {
    root.innerHTML = "";
    return;
  }
  const p = state.flash;
  root.innerHTML = `
    <div class="flash" role="dialog" aria-modal="true">
      <div class="flash-card">
        <p class="ko">${esc(p.koreanTitle)}</p>
        <p class="ja-big">${esc(p.japaneseText)}</p>
        <p class="pron">${esc(p.pronunciation)}</p>
        <p class="muted">${esc(p.situationTip)}</p>
        <div class="actions" style="justify-content:center;margin-top:18px">
          <button type="button" class="btn btn-primary" id="speak-btn">일본어 읽기</button>
          <button type="button" class="btn btn-ghost" id="close-flash">닫기</button>
        </div>
      </div>
    </div>`;
  root.querySelector("#close-flash").onclick = () => {
    state.flash = null;
    renderModal();
  };
  root.querySelector("#speak-btn").onclick = () => speak(p.japaneseText);
  root.querySelector(".flash").addEventListener("click", (e) => {
    if (e.target.classList.contains("flash")) {
      state.flash = null;
      renderModal();
    }
  });
}

boot().catch((err) => {
  document.getElementById("main").innerHTML = `<p class="empty">데이터를 불러오지 못했습니다.<br>${esc(err.message)}</p>`;
});
