package com.example.data

data class AlternativeActivity(
    val id: String,
    val dayNumber: Int,
    val originalSpot: String,
    val situation: String,             // "우천/악천후 (비 올 때)", "교통 지연/혼잡 시", "정기 휴무/대기 과다 시"
    val alternativeTitle: String,
    val alternativeLocation: String,
    val transitGuide: String,
    val description: String,
    val indoorAdvantage: String,       // 실내 시설 장점 & 아이 친화 포인트
    val nearbyFoodTip: String          // 회/내장 없는 인근 맛집
)

data class RealtimeTrafficLink(
    val title: String,
    val lineName: String,
    val url: String,
    val description: String,
    val delayTip: String
)

data class CityWeatherGuide(
    val cityName: String,
    val region: String,
    val officialUrl: String,
    val avgTemp: String,
    val weatherTips: String,
    val rainyAlternativeSummary: String
)

data class EmergencyJapanesePhrase(
    val id: Int,
    val category: String,             // "교통 지연·길찾기", "아이 질환·약국·응급", "분실·경찰·도움", "식당 주문 (회·내장 제외)"
    val koreanTitle: String,
    val japaneseText: String,
    val pronunciation: String,
    val situationTip: String
)

object ContingencyData {

    // ==========================================
    // 1. 대체 경로 및 활동 제안 (Day 1 - 6)
    // ==========================================
    val alternativeActivities = listOf(
        // Day 1
        AlternativeActivity(
            id = "d1_osaka_castle_rain",
            dayNumber = 1,
            originalSpot = "오사카 캐슬 (천수각 야외 공원)",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "오사카 역사박물관 (실내 체험관) & 카이유칸(해유관)",
            alternativeLocation = "오사카 역사박물관 (다니마치 4초메역 9번 출구 직결)",
            transitGuide = "오사카성 정문 바로 맞은편 (도보 3분). 비가 많이 올 경우 지하철 주오선 타고 18분 '오사카코역' 이동하여 카이유칸 수족관으로 전환 가능.",
            description = "오사카성 천수각 야외 계단 오르기가 비로 미끄럽고 불편할 때, 바로 맞은편 유리 건물인 역사박물관 10층~7층 실내에서 고대 난바궁궐과 에도시대 거리를 실물 크기로 재현한 전시를 쾌적하게 관람할 수 있습니다. 10층 유리창에서 비 내리는 오사카성 전경을 한눈에 감상 가능.",
            indoorAdvantage = "비 한 방울 맞지 않는 실내 에어컨 완비! 고대 전통의상 입어보기 체험과 고고학 발굴 퍼즐 등 초등학생 참여형 프로그램 풍부.",
            nearbyFoodTip = "역사박물관 1층 NHK 방송국 내 카페테리아 & 다니마치4초메역 인근 '우동 큐타로' (따뜻한 고기우동)"
        ),
        AlternativeActivity(
            id = "d1_shinsekai_rain",
            dayNumber = 1,
            originalSpot = "신세카이 시장 & 츠텐카쿠 야외 거리",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "스파월드 세계의 대온천 (실내 테마파크) & 메가돈키호테",
            alternativeLocation = "스파월드 (츠텐카쿠 타워 도보 1분)",
            transitGuide = "도부츠엔마에역 5번 출구 바로 앞. 전 구역 실내 연결.",
            description = "비 오는 날 신세카이 골목 투어 대신 8층 규모의 거대 실내 온천 테마파크인 스파월드에서 아시아존/유럽존 온천과 실내 온수 키즈풀(슬라이더 포함)을 즐깁니다. 아이들과 여행 피로를 풀고 바로 옆 대형 실내몰 메가 돈키호테 신세카이점에서 비 안 맞고 쇼핑 가능.",
            indoorAdvantage = "실내 온수 수영장과 키즈 슬라이더 구비, 타월/어메니티 무료 제공으로 빈손 방문 가능.",
            nearbyFoodTip = "스파월드 3층 푸드코트: 수제 돈까스 정식, 어린이 우동 세트, 소프트아이스크림 (회·내장 0%)"
        ),

        // Day 2
        AlternativeActivity(
            id = "d2_kyoto_storm",
            dayNumber = 2,
            originalSpot = "교토 버스 투어 (청수사·금각사 야외 도보)",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "교토 철도박물관 & 교토 아쿠아리움 (실내)",
            alternativeLocation = "우메코지 공원 내 (교토역에서 사가노선 1정거장 '우메코지교토니시역' 도보 2분)",
            transitGuide = "교토역에서 JR 사가노선으로 1정거장 (3분). 역 출구부터 박물관 입구까지 지붕 캐노피 설치.",
            description = "비바람으로 청수사의 좁은 언덕길과 계단이 미끄러울 때, 일본 최대급 실내 철도박물관으로 이동합니다. 실제 증기기관차부터 신칸센 500계 등 53량의 실물 기차가 전시되어 있고, 실제 신칸센 운전 시뮬레이터와 거대 디오라마 쇼를 관람할 수 있습니다.",
            indoorAdvantage = "기차를 좋아하는 초등학생들의 최고 인기 명소! 증기기관차 탑승 체험(SL 스팀호)과 철도 제복 체험 가능.",
            nearbyFoodTip = "박물관 2층 레스토랑: 닥터 옐로우(신칸센) 키즈 오므라이스 도시락, 비프 카레라이스"
        ),
        AlternativeActivity(
            id = "d2_dotonbori_crowd",
            dayNumber = 2,
            originalSpot = "도톤보리 야외 번화가 & 신사이바시 야경",
            situation = "우천/혼잡 시 (비 올 때)",
            alternativeTitle = "난바워크(Namba Walk) 지하 아케이드 & 빅카메라 난바",
            alternativeLocation = "난바역 ~ 닛폰바시역을 잇는 거대 지하 쇼핑상가",
            transitGuide = "지하철 난바역 및 킨테츠 난바역에서 지하로 직결 (지상으로 나가지 않고 도보 이동)",
            description = "도톤보리 지상 야외에 비가 쏟아지거나 인파가 너무 많아 아이 손을 놓칠 염려가 있을 때, 715m 길이의 쾌적한 지하상가 '난바워크'로 대피합니다. 분수 광장, 캐릭터 숍, 디저트 카페가 즐비하며 빅카메라 난바점 7층 대형 완구 코너와 실내로 연결됩니다.",
            indoorAdvantage = "우산 없이 유모차 및 어린이 도보 이동 완벽 가능. 깨끗한 가족 화장실과 수유실 다수 완비.",
            nearbyFoodTip = "난바워크 1번가 '키네야 난바점' (즉석 수타 수제우동, 카츠동, 닭튀김 가라아게 정식)"
        ),

        // Day 3
        AlternativeActivity(
            id = "d3_shopping_heavy_rain",
            dayNumber = 3,
            originalSpot = "오사카 시내 쇼핑 (우메다 야외 거리)",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "그랜드 프론트 오사카 & 루쿠아(LUCUA) 복합몰",
            alternativeLocation = "JR 오사카역 / 우메다역 직결 (공중데크 및 지하 연결통로)",
            transitGuide = "미도스지선 우메다역 또는 JR 오사카역에서 지하/실내 브릿지로 직통 연결",
            description = "비 내리는 날 우산 없이 오사카 최대 복합 실내 쇼핑몰을 탐방합니다. 그랜드 프론트 북관에는 아이들이 첨단 IT/로봇 기술을 체험할 수 있는 'KNOWLEDGE CAPITAL'이 있으며, 다이마루 백화점 13층에는 세계 최대급 포켓몬센터 오사카와 닌텐도 오사카가 있습니다.",
            indoorAdvantage = "외부 비바람에 전혀 노출되지 않고 쇼핑, 놀이, 식사를 한 건물에서 원스톱 해결 가능.",
            nearbyFoodTip = "루쿠아 지하 2층 푸드홀: '바쿠로 돈카츠' & '동양정' (알루미늄 포일에 싸인 100년 전통 함박스테이크)"
        ),
        AlternativeActivity(
            id = "d3_onsen_alternative",
            dayNumber = 3,
            originalSpot = "야외 노천온천",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "소라니와 온천 (Solaniwa Onsen - 간사이 최대 실내 온천 테마파크)",
            alternativeLocation = "JR 간사이 본선/오사카 순환선 '벤텐초역' 2A 출구 직결",
            transitGuide = "우메다(오사카역)에서 JR 순환선 탑승 시 9분 만에 벤텐초역 도착. 역에서 실내 연결통로 이용.",
            description = "아즈치모모야마 시대를 재현한 3,000평 규모의 간사이 최대 실내 온천 테마파크. 온 가족이 원하는 화려한 유카타를 골라 입고 에도시대 저잣거리를 거닐며 실내 족욕탕, 미니 게임, 온천욕을 비 걱정 없이 즐길 수 있습니다.",
            indoorAdvantage = "실내 족욕 정원과 만화방 1만 권 구비, 레트로 게임존이 있어 비 오는 날 3~4시간 힐링에 안성맞춤.",
            nearbyFoodTip = "온천 내 식당가: 에도시대풍 카레우동, 삼겹살 덮밥, 꼬치구이(닭고기), 당고 디저트"
        ),

        // Day 4
        AlternativeActivity(
            id = "d4_shinkansen_delay",
            dayNumber = 4,
            originalSpot = "신오사카 -> 고쿠라 신칸센 이동",
            situation = "교통 지연/혼잡 시",
            alternativeTitle = "신칸센 지연 시 역무원 대처 & 신오사카 에키마르쉐 실내 대기",
            alternativeLocation = "신오사카역 신칸센 개찰구 및 역사 3층 에키마르쉐",
            transitGuide = "열차 지연 시 개찰구 전광판 확인 후 '미도리노 마도구치(녹색 창구)'에서 후속 편 무료 변경 요청.",
            description = "폭우나 강풍으로 산요 신칸센이 일시 운행 보류 또는 지연될 경우, 당황하지 않고 신오사카역 3층 실내 상업시설 '에키마르쉐'에서 대기합니다. 신칸센 지연증명서는 모바일 또는 창구에서 발급되며, 지정석 열차가 멈출 경우 후속 노조미/사쿠라 열차의 자유석 또는 변경 편을 즉시 이용할 수 있습니다.",
            indoorAdvantage = "신오사카역 내 대형 대기실, 콘센트 라운지, 포켓와이파이 및 벤토 전문점 밀집.",
            nearbyFoodTip = "에키마르쉐 신오사카: '다루마' 돈카츠 샌드위치, '고고카레' 돈까스 카레"
        ),
        AlternativeActivity(
            id = "d4_kokura_rain",
            dayNumber = 4,
            originalSpot = "고쿠라성 야외 정원",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "기타큐슈 만화박물관 & 아루아루시티 (Aruaru City)",
            alternativeLocation = "고쿠라역 북쪽 출구(신칸센 출구) 도보 2분 (공중 보행데크 지붕 연결)",
            transitGuide = "고쿠라역 개찰구에서 북쪽 신칸센구로 나와 지붕 있는 페데스트리안 데크를 따라 직진.",
            description = "고쿠라성 정원에 비가 올 때, 고쿠라역 바로 뒤편의 서브컬처 복합 실내몰 '아루아루시티'와 5~6층 '기타큐슈 만화박물관'을 방문합니다. 은하철도 999의 원작자 마쓰모토 레이지의 고향으로, 실물 크기 차장 피규어와 5만 권의 만화책 열람실, 만화 그리기 체험 코너가 있습니다.",
            indoorAdvantage = "역에서 비를 맞지 않고 도보 이동 가능. 만화책 열람과 캐릭터 굿즈 쇼핑을 동시에 즐김.",
            nearbyFoodTip = "아루아루시티 1층 & 고쿠라역 지하: '시로야 베이커리' 써니빵 & '스케상 우동' (고기 우엉튀김 우동)"
        ),

        // Day 5
        AlternativeActivity(
            id = "d5_mojiko_rain",
            dayNumber = 5,
            originalSpot = "모지코 레트로 야외 산책 & 간몬 유람선",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "큐슈 철도기념관 & 간몬 해저 인도 터널 (바다 속 도보 횡단)",
            alternativeLocation = "큐슈 철도기념관 (모지코역 바로 옆) / 간몬터널 (해저 58m)",
            transitGuide = "모지코역에서 도보 1분 큐슈 철도기념관. 간몬 터널은 비바람이 몰아쳐도 해저 엘리베이터를 타고 완벽한 실내에서 바다 밑 780m를 건널 수 있음.",
            description = "해변가 모지코에 바닷바람과 비가 불 때, 큐슈 철도기념관 실내 본관에서 메이지 시대 객차와 실물 기차 운전 시뮬레이터를 체험합니다. 또한 유람선이 결항되더라도 '간몬 해저 인도 터널'을 이용하면 비 한 방울 맞지 않고 후쿠오카현에서 야마구치현 시모노세키로 바다 밑을 걸어서 건널 수 있습니다.",
            indoorAdvantage = "전천후 날씨 무관! 해저 현 경계선(후쿠오카-야마구치)에서 가족 발도장 기념사진 촬영 인기.",
            nearbyFoodTip = "모지코 레트로 '베어 프루츠(Bear Fruits)' 본점: 뚝배기 치즈 야키카레 (맵지 않은 순한맛 선택 가능, 회·내장 0%)"
        ),

        // Day 6
        AlternativeActivity(
            id = "d6_sarakura_wind",
            dayNumber = 6,
            originalSpot = "사라쿠라산 케이블카 & 슬로프카",
            situation = "강풍/기상 악화로 케이블카 운휴 시",
            alternativeTitle = "스페이스 LABO (THE OUTLETS KITAKYUSHU 내 대형 실내 과학관)",
            alternativeLocation = "JR 가고시마 본선 '스페이스월드역' 도보 2분",
            transitGuide = "고쿠라역에서 쾌속 열차로 10분 '스페이스월드역' 하차. 디 아울렛 기타큐슈 실내로 바로 연결.",
            description = "사라쿠라산 케이블카는 초속 15m 이상 강풍 시 안전을 위해 운휴됩니다. 이 경우 대체지로 바로 근처의 '스페이스 LABO(기타큐슈시 이과관)'를 방문합니다. 일본 서부 최대급 30m 돔 플라네타륨(천체투영관), 국내 최대 크기의 대형 토네이도 발생 장치, 우주 무중력 체험이 마련되어 있습니다.",
            indoorAdvantage = "최신 2022년 오픈 대형 실내 과학관. 아울렛 쇼핑몰과 연결되어 귀국 전 선물 쇼핑도 겸할 수 있음.",
            nearbyFoodTip = "디 아울렛 푸드코트: '텐푸라 타카오' (바삭한 즉석 새우·고구마·단호박 튀김 정식)"
        )
    )

    // ==========================================
    // 2. 실시간 교통 & 날씨 연동 링크 정보
    // ==========================================
    val trafficLinks = listOf(
        RealtimeTrafficLink(
            title = "JR 서일본 간사이 열차 운행정보",
            lineName = "하루카·신쾌속·오사카 순환선",
            url = "https://trafficinfo.westjr.co.jp/kinki.html",
            description = "간사이 공항 ↔ 신오사카 특급 하루카 및 교토·고베행 신쾌속의 지연/사고 여부를 실시간으로 한국어 확인 가능합니다.",
            delayTip = "15분 이상 지연 시 사이트에 붉은색 경고 표시와 지연 시간이 분 단위로 갱신됩니다."
        ),
        RealtimeTrafficLink(
            title = "산요 신칸센 실시간 운행정보",
            lineName = "신오사카 ↔ 고쿠라·하카타",
            url = "https://trafficinfo.westjr.co.jp/sanyo.html",
            description = "9/23 오사카에서 기타큐슈로 이동하는 산요 신칸센(노조미·사쿠라·미즈호)의 실시간 정시 운행 현황입니다.",
            delayTip = "태풍·폭우 시 속도 규제가 발생할 수 있으며, 지연 시 역 개찰구에서 '지연 증명서'를 발급받으세요."
        ),
        RealtimeTrafficLink(
            title = "JR 큐슈 열차 실시간 운행정보",
            lineName = "가고시마 본선·모지코·고쿠라",
            url = "https://www.jrkyushu.co.jp/trains/unkou.php",
            description = "기타큐슈 주유 패스로 탑승하는 모지코행, 스페이스월드행 JR 전철의 운행 상태를 실시간 제공합니다.",
            delayTip = "강풍에 취약한 해안선(모지코 방면) 운행 여부를 탑승 전 사전 확인하면 좋습니다."
        ),
        RealtimeTrafficLink(
            title = "오사카 메트로(지하철) 운행현황",
            lineName = "미도스지선·주오선·사카이스지선",
            url = "https://subway.osakametro.co.jp/guide/subway_information.php",
            description = "호텔 앞 니시나카지마역을 지나는 미도스지선과 오사카성·신세카이 노선의 운행 상태를 점검합니다.",
            delayTip = "지하철은 날씨 영향을 거의 받지 않으므로 폭우 시 가장 안전한 이동 수단입니다."
        )
    )

    val weatherGuides = listOf(
        CityWeatherGuide(
            cityName = "오사카 (Osaka)",
            region = "간사이 (9/20 - 9/22 일정)",
            officialUrl = "https://weather.yahoo.co.jp/weather/jp/27/6200.html",
            avgTemp = "22℃ ~ 28℃ (낮에는 초여름 기온)",
            weatherTips = "9월 말 오사카는 낮에는 반팔, 저녁에는 얇은 겉옷이 적당합니다. 갑작스러운 게릴라성 소나기에 대비해 3단 접이식 우산을 가방에 상시 소지하세요.",
            rainyAlternativeSummary = "오사카성 대신 역사박물관/카이유칸, 신세카이 대신 스파월드/하루카스300 실내몰 이용 권장"
        ),
        CityWeatherGuide(
            cityName = "교토 (Kyoto)",
            region = "간사이 (9/21 일정)",
            officialUrl = "https://weather.yahoo.co.jp/weather/jp/26/6110.html",
            avgTemp = "20℃ ~ 27℃ (분지 지형으로 일교차 큼)",
            weatherTips = "교토는 분지 지형이라 비가 오면 기온이 급격히 떨어질 수 있습니다. 아이들의 바람막이 점퍼를 꼭 챙기세요.",
            rainyAlternativeSummary = "우천 시 교토 철도박물관(실내) 및 교토 아쿠아리움, 테라마치 아케이드 상점가 강력 추천"
        ),
        CityWeatherGuide(
            cityName = "기타큐슈 (고쿠라/모지코)",
            region = "큐슈 북부 (9/23 - 9/25 일정)",
            officialUrl = "https://weather.yahoo.co.jp/weather/jp/40/8220.html",
            avgTemp = "21℃ ~ 27℃ (해안가 바닷바람 강함)",
            weatherTips = "모지코와 간몬 해협은 바닷바람이 강하므로 모자가 날아가지 않도록 주의하고, 사라쿠라산 케이블카 탑승 전 당일 풍속을 확인하세요.",
            rainyAlternativeSummary = "강풍·우천 시 간몬 해저 인도 터널, 큐슈 철도기념관, 스페이스 LABO 과학관 이용"
        )
    )

    // ==========================================
    // 3. 비상 상황 일본어 회화 문구 (TTS & 큰 글씨 지원)
    // ==========================================
    val emergencyPhrases = listOf(
        // Category 1: 교통 지연 & 길 찾기
        EmergencyJapanesePhrase(
            id = 101,
            category = "교통 지연·길찾기",
            koreanTitle = "열차가 지연되었나요? 대체 노선이 있나요?",
            japaneseText = "電車は遅延していますか？代替ルートはありますか？",
            pronunciation = "덴샤와 치엔 시테이마스카? 다이타이 루-토와 아리마스카?",
            situationTip = "역 전광판에 빨간 글씨가 뜨거나 열차가 오지 않을 때 역무원에게 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 102,
            category = "교통 지연·길찾기",
            koreanTitle = "신오사카역(고쿠라역)으로 가려면 어떻게 가야 하나요?",
            japaneseText = "新大阪駅（小倉駅）へ行くには、どう行けばいいですか？",
            pronunciation = "신오사카에키 (고쿠라에키)에 이쿠니와, 도- 이케바 이이데스카?",
            situationTip = "환승이나 길을 헤맬 때 주변 역무원이나 안내센터 직원에게 화면을 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 103,
            category = "교통 지연·길찾기",
            koreanTitle = "길을 잃었습니다. 여기가 어디인지 지도에서 알려주세요.",
            japaneseText = "道に迷いました。現在地を地図で教えていただけますか？",
            pronunciation = "미치니 마요이마시타. 겐자이치오 치즈데 오시에테 이타다케마스카?",
            situationTip = "스마트폰 지도를 가리키며 현지인에게 정중하게 도움을 요청할 때 사용합니다."
        ),
        EmergencyJapanesePhrase(
            id = 104,
            category = "교통 지연·길찾기",
            koreanTitle = "다음 열차(버스)는 몇 번 승강장에서 타나요?",
            japaneseText = "次の列車（バス）は何番ホームから乗ればいいですか？",
            pronunciation = "츠기노 렛샤 (바스)와 난반 호-무카라 노레바 이이데스카?",
            situationTip = "플랫폼 번호가 헷갈리거나 행선지 방향을 재확인할 때 유용합니다."
        ),

        // Category 2: 아이 질환 & 약국/병원/구급차
        EmergencyJapanesePhrase(
            id = 201,
            category = "아이 질환·약국·응급",
            koreanTitle = "아이가 열이 나고 배가 아픕니다. 가까운 소아과가 어디인가요?",
            japaneseText = "子供が急に熱を出して腹痛があります。近くの小児科はどこですか？",
            pronunciation = "코도모가 큐-니 네츠오 다시테 후쿠츠-가 아리마스. 치카쿠노 쇼-니카와 도코데스카?",
            situationTip = "호텔 프론트나 주변 상점에 가장 가까운 소아과 병원을 물어볼 때 즉시 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 202,
            category = "아이 질환·약국·응급",
            koreanTitle = "초등학생용 해열진통제(약)가 있나요?",
            japaneseText = "小学生用の解熱鎮痛薬はありますか？（子供用バファリンなど）",
            pronunciation = "쇼-갓세-요-노 게네츠 진츠-야쿠와 아리마스카? (코도모요- 바파린 나도)",
            situationTip = "드럭스토어(마츠모토 키요시 등)에서 어린이 전용 상비약을 구매할 때 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 203,
            category = "아이 질환·약국·응급",
            koreanTitle = "아이가 멀미를 합니다. 어린이용 멀미약 주세요.",
            japaneseText = "子供が乗り物酔いをしています。子供用の酔い止め薬をください。",
            pronunciation = "코도모가 노리모노요이오 시테이마스. 코도모요-노 요이도메야쿠오 쿠다사이.",
            situationTip = "버스 투어나 유람선, 신칸센 탑승 전 드럭스토어에서 멀미약을 찾을 때 유용합니다."
        ),
        EmergencyJapanesePhrase(
            id = 204,
            category = "아이 질환·약국·응급",
            koreanTitle = "긴급 상황입니다! 구급차를 빨리 불러주세요! (119)",
            japaneseText = "緊急事態です！救急車を早く呼んでください！",
            pronunciation = "킨큐- 지타이데스! 큐-큐-샤오 하야쿠 욘데 쿠다사이!",
            situationTip = "아이가 크게 다치거나 응급 처치가 필요할 때 호텔이나 주변 사람에게 외치거나 보여주세요."
        ),

        // Category 3: 분실·경찰·도움
        EmergencyJapanesePhrase(
            id = 301,
            category = "분실·경찰·도움",
            koreanTitle = "지갑(여권, 스마트폰)을 잃어버렸습니다. 파출소(코반)가 어디인가요?",
            japaneseText = "財布（パスポート、スマホ）を紛失しました。交番はどこですか？",
            pronunciation = "사이후 (파스포-토, 스마호)오 훈시츠 시마시타. 코-반와 도코데스카?",
            situationTip = "소지품을 분실했을 때 가까운 파출소(KOBAN) 위치를 물어볼 때 사용합니다."
        ),
        EmergencyJapanesePhrase(
            id = 302,
            category = "분실·경찰·도움",
            koreanTitle = "분실물 신고서(유실물 확인증)를 작성하고 싶습니다.",
            japaneseText = "遺失届出証明書を作成していただきたいです。",
            pronunciation = "이시츠 토도케데 쇼-메-쇼오 사쿠세- 시테 이타다키타이데스.",
            situationTip = "파출소나 역 유실물 센터에서 여행자 보험 청구 및 여권 재발급에 필요한 증명서를 작성할 때 씁니다."
        ),
        EmergencyJapanesePhrase(
            id = 303,
            category = "분실·경찰·도움",
            koreanTitle = "한국어를 할 수 있는 직원이나 통역 서비스가 있나요?",
            japaneseText = "韓国語ができるスタッフ、または通訳サービスはありますか？",
            pronunciation = "칸코쿠고가 데키루 스탓후, 마타와 츠-야쿠 사-비수와 아리마스카?",
            situationTip = "공항, 대형 역 창구, 경찰서 등에서 3자 통역 전화를 요청할 때 보여주세요."
        ),

        // Category 4: 식당 안심 주문 (회·내장 제외 & 알레르기)
        EmergencyJapanesePhrase(
            id = 401,
            category = "식당 주문 (회·내장 제외)",
            koreanTitle = "아이가 날생선(회)과 내장(호르몬)을 전혀 못 먹습니다.",
            japaneseText = "子供が生魚（お刺身）とホルモン（内臓）が全く食べられません。",
            pronunciation = "코도모가 나마자카나 (오사시미)토 호루몬 (나이조-)가 맛타쿠 타베라레마센.",
            situationTip = "식당 주문 전 점원에게 보여주면 날것이나 내장 재료가 들어가지 않은 메뉴를 권해줍니다."
        ),
        EmergencyJapanesePhrase(
            id = 402,
            category = "식당 주문 (회·내장 제외)",
            koreanTitle = "모든 고기는 완전히 속까지 익혀서 조리해 주세요.",
            japaneseText = "お肉は中までしっかり火を通してください（ウェルダン）。",
            pronunciation = "오니쿠와 나카마데 싯카리 히오 토-시테 쿠다사이 (웨루단).",
            situationTip = "돈카츠, 규카츠, 함박스테이크 주문 시 덜 익은 붉은 기가 없도록 요청할 때 필수적입니다."
        ),
        EmergencyJapanesePhrase(
            id = 403,
            category = "식당 주문 (회·내장 제외)",
            koreanTitle = "와사비는 빼고 조리해 주세요. (사비누키)",
            japaneseText = "わさび抜きでお願いします。",
            pronunciation = "와사비 누키데 오네가이시마스.",
            situationTip = "아이들이 매운 와사비 때문에 식사를 못 하지 않도록 주문 시 꼭 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 404,
            category = "식당 주문 (회·내장 제외)",
            koreanTitle = "어린이용 식기(포크, 스푼, 작은 그릇)를 부탁드립니다.",
            japaneseText = "子供用の取り皿とフォーク、スプーンをお願いします。",
            pronunciation = "코도모요-노 토리자라토 포-쿠, 스푸-오 오네가이시마스.",
            situationTip = "식당 테이블에 젓가락만 있을 때 아이용 식기를 간편하게 요청할 수 있습니다."
        )
    )
}
