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
    val category: String,             // "교통 지연·길찾기", "부모님 질환·약국·응급", "분실·경찰·도움", "식당 주문 (회·내장 제외)"
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
            alternativeTitle = "오사카 역사박물관 (실내 관람) & 쾌적한 엘리베이터 투어",
            alternativeLocation = "오사카 역사박물관 (다니마치 4초메역 9번 출구 직결)",
            transitGuide = "오사카성 정문 바로 맞은편 (도보 3분). 비가 올 경우 지하철 연결통로로 쾌적하게 이동 가능.",
            description = "오사카성 천수각 야외 계단 오르기가 비로 미끄럽고 부모님 무릎에 부담될 때, 바로 맞은편 현대식 건물인 역사박물관으로 이동합니다. 10층부터 7층까지 완만한 에스컬레이터와 엘리베이터로 고대 나니와궁과 에도시대 거리를 실내에서 편안히 관람하며, 10층 통유리창으로 오사카성 전경을 조망합니다.",
            indoorAdvantage = "전 층 엘리베이터 완비 및 비 한 방울 맞지 않는 실내 냉난방 환경. 부모님께서 다리 아프지 않게 관람 가능.",
            nearbyFoodTip = "역사박물관 1층 NHK 방송국 내 카페테리아 & 다니마치4초메역 인근 '우동 큐타로' (담백한 온우동)"
        ),
        AlternativeActivity(
            id = "d1_shinsekai_rain",
            dayNumber = 1,
            originalSpot = "신세카이 시장 & 츠텐카쿠 야외 거리",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "아베노 하루카스 300 (실내 전망대 & 긴테쓰 백화점)",
            alternativeLocation = "덴노지역 직결 아베노 하루카스 타워",
            transitGuide = "도부츠엔마에역에서 미도스지선 1정거장 (덴노지역 지하 직결, 도보 2분).",
            description = "비 오는 날 노면이 젖은 신세카이 골목 대신, 덴노지역과 바로 연결된 일본 최고층 빌딩 아베노 하루카스 300 실내 전망대와 긴테쓰 백화점을 방문합니다. 360도 통유리로 오사카 야경을 감상하고, 품격 있는 백화점 식당가와 전통 다과점에서 여유로운 시간을 보낼 수 있습니다.",
            indoorAdvantage = "완전 실내 역세권 직결. 휠체어/유모차 전용 동선 및 부모님을 위한 안락한 휴식 공간 완비.",
            nearbyFoodTip = "긴테쓰 백화점 12~14층 다이닝: '동양정' 100년 전통 함박스테이크, '마이센' 부드러운 안심 돈카츠 (회·내장 제로)"
        ),

        // Day 2
        AlternativeActivity(
            id = "d2_kyoto_storm",
            dayNumber = 2,
            originalSpot = "교토 버스 투어 (청수사·금각사 야외 도보)",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "교토역 이세탄 백화점 & 교토 국립박물관 (실내 문화투어)",
            alternativeLocation = "JR 교토역 역사 및 시치조 교토 국립박물관",
            transitGuide = "교토역 역사 내 이세탄 백화점 직결 또는 교토역에서 택시 기본요금(약 800엔)으로 국립박물관 이동.",
            description = "비바람으로 청수사의 좁은 언덕길과 돌계단이 미끄러워 부모님 낙상 위험이 있을 때, 투어 가이드와 상의 후 교토역 실내 복합 문화공간 또는 교토 국립박물관의 평지 실내 전시를 관람합니다. 천년 고도의 국보급 불교 미술과 공예품을 품격 있게 감상할 수 있습니다.",
            indoorAdvantage = "단차 없는 배리어프리 평지 이동 및 계단 없는 엘리베이터 동선. 조용하고 정갈한 문화 휴식.",
            nearbyFoodTip = "교토역 이세탄 백화점 11층 식당가: 교토 전통 두부 요리 '후와리' & 정갈한 소고기 스키야키 정식"
        ),
        AlternativeActivity(
            id = "d2_dotonbori_crowd",
            dayNumber = 2,
            originalSpot = "도톤보리 야외 번화가 & 신사이바시 야경",
            situation = "우천/혼잡 시 (비 올 때)",
            alternativeTitle = "난바워크(Namba Walk) 지하 아케이드 & 다카시마야 백화점",
            alternativeLocation = "난바역 ~ 닛폰바시역을 잇는 거대 지하 쇼핑상가",
            transitGuide = "지하철 난바역에서 지하로 직결 (지상으로 나가지 않고 쾌적하게 이동)",
            description = "도톤보리 야외에 비가 쏟아지거나 관광객 인파가 너무 많아 걷기 불편할 때, 715m 길이의 쾌적한 지하 아케이드 '난바워크'와 다카시마야 백화점으로 이동합니다. 지붕 있는 평지길을 따라 일본 전통 찻집, 공예품점, 제과점이 이어져 부모님과 안전하고 여유롭게 산책할 수 있습니다.",
            indoorAdvantage = "비 걱정 없이 넓고 쾌적한 평지 보행로. 층간 이동 없이 완만한 도보 코스.",
            nearbyFoodTip = "난바워크 1번가 '키네야 난바점' (즉석 수타 수제 온우동, 닭튀김 정식)"
        ),

        // Day 3
        AlternativeActivity(
            id = "d3_shopping_heavy_rain",
            dayNumber = 3,
            originalSpot = "오사카 시내 쇼핑 (우메다 야외 거리)",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "한큐 백화점 본점 & 그랜드 프론트 오사카 (실내 연결)",
            alternativeLocation = "JR 오사카역 / 우메다역 직결 (공중데크 및 지하 연결통로)",
            transitGuide = "미도스지선 우메다역에서 지하 아케이드로 직통 연결",
            description = "비 내리는 날 우산 없이 오사카 최대 품격 백화점 한큐 본점과 그랜드 프론트를 탐방합니다. 부모님을 위한 최고급 손수건, 우지 말차, 전통 과자 선물을 둘러보며 실내 카페 라운지에서 품격 있는 커피와 디저트를 즐길 수 있습니다.",
            indoorAdvantage = "외부 비바람에 전혀 노출되지 않고 쇼핑, 카페, 휴식을 쾌적한 실내에서 원스톱 해결.",
            nearbyFoodTip = "한큐 백화점 12층 다이닝: '동양정' 100년 전통 함박스테이크 & '미요시' 담백한 소고기 솥밥"
        ),
        AlternativeActivity(
            id = "d3_onsen_alternative",
            dayNumber = 3,
            originalSpot = "야외 노천온천",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "소라니와 온천 (Solaniwa Onsen - 간사이 최대 실내 온천 테마파크)",
            alternativeLocation = "JR 간사이 본선/오사카 순환선 '벤텐초역' 2A 출구 직결",
            transitGuide = "우메다(오사카역)에서 JR 순환선 탑승 시 9분 만에 벤텐초역 도착. 역에서 실내 연결통로 이용.",
            description = "아즈치모모야마 시대를 재현한 3,000평 규모의 간사이 최대 실내 온천 테마파크. 부모님과 함께 편안한 실내 족욕탕, 탄산천, 제트스파를 즐기며 비 오는 날 여행 피로를 완벽하게 풀 수 있습니다.",
            indoorAdvantage = "실내 족욕 정원과 편안한 리클라이너 휴식 라운지 완비. 날씨 무관 힐링.",
            nearbyFoodTip = "온천 내 식당가: 정갈한 온우동, 소고기 덮밥, 당고 및 호지차 디저트"
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
            description = "폭우나 강풍으로 산요 신칸센이 일시 운행 보류 또는 지연될 경우, 당황하지 않고 신오사카역 3층 실내 상업시설 '에키마르쉐'의 편안한 대기석에서 휴식합니다. 신칸센 지연증명서는 창구에서 발급되며, 지정석 열차가 멈출 경우 후속 열차의 좌석을 무료로 즉시 재지정받을 수 있습니다.",
            indoorAdvantage = "신오사카역 내 대형 대기실, 휠체어 대응 편의시설, 에키벤 전문점 밀집.",
            nearbyFoodTip = "에키마르쉐 신오사카: '다루마' 부드러운 돈카츠 샌드위치, 정갈한 에키벤 도시락"
        ),
        AlternativeActivity(
            id = "d4_kokura_rain",
            dayNumber = 4,
            originalSpot = "고쿠라성 야외 정원",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "리버워크 기타큐슈 (대형 복합 실내몰) & 고쿠라성 역사전시실",
            alternativeLocation = "고쿠라성 바로 옆 리버워크 기타큐슈",
            transitGuide = "고쿠라역에서 버스로 4분 또는 도보 10분 (성 내부 엘리베이터 및 리버워크 실내 연결).",
            description = "고쿠라성 정원에 비가 올 때, 고쿠라성 천수각 내부의 현대식 엘리베이터를 타고 쾌적하게 역사 전시를 관람한 후, 바로 옆 대형 실내 문화복합몰인 리버워크 기타큐슈로 이동합니다. 통유리로 비 내리는 고쿠라성을 바라보며 실내 카페에서 차 한 잔의 여유를 즐길 수 있습니다.",
            indoorAdvantage = "고쿠라성 내부 엘리베이터 완비로 계단 보행 불필요. 리버워크 실내 통로로 비바람 차단.",
            nearbyFoodTip = "리버워크 4층 식당가: '텐푸라 타카오' 즉석 수제 튀김 정식 & '스케상 우동' (소고기 온우동)"
        ),

        // Day 5
        AlternativeActivity(
            id = "d5_mojiko_rain",
            dayNumber = 5,
            originalSpot = "모지코 레트로 야외 산책 & 간몬 유람선",
            situation = "우천/악천후 (비 올 때)",
            alternativeTitle = "큐슈 철도기념관 (실내 역사관) & 간몬 해저 인도 터널 (바다 속 도보)",
            alternativeLocation = "큐슈 철도기념관 (모지코역 바로 옆) / 간몬터널 (해저 55m)",
            transitGuide = "모지코역에서 도보 1분 큐슈 철도기념관. 간몬 터널은 비바람이 몰아쳐도 해저 엘리베이터를 타고 완벽한 실내에서 바다 밑 780m를 건널 수 있음.",
            description = "해변가 모지코에 바닷바람과 비가 불 때, 큐슈 철도기념관 실내 본관에서 메이지 시대 고풍스러운 목조 객차를 관람합니다. 또한 유람선이 결항되더라도 '간몬 해저 인도 터널'을 이용하면 비 한 방울 맞지 않고 후쿠오카현에서 야마구치현 시모노세키로 바다 밑을 걸어서 건널 수 있습니다.",
            indoorAdvantage = "전천후 날씨 무관! 해저 현 경계선(후쿠오카-야마구치)에서 부모님 기념사진 촬영 인기.",
            nearbyFoodTip = "모지코 레트로 '베어 프루츠(Bear Fruits)' 본점: 뚝배기 치즈 야키카레 (맵지 않은 순한맛 선택 가능, 회·내장 0%)"
        ),

        // Day 6
        AlternativeActivity(
            id = "d6_sarakura_wind",
            dayNumber = 6,
            originalSpot = "사라쿠라산 케이블카 & 슬로프카 / 디 아울렛",
            situation = "강풍/기상 악화로 케이블카 운휴 시",
            alternativeTitle = "디 아울렛 기타큐슈 & 실내 플라네타륨",
            alternativeLocation = "JR 가고시마 본선 '스페이스월드역' 도보 2분",
            transitGuide = "고쿠라역에서 쾌속 열차로 10분 '스페이스월드역' 하차. 디 아울렛 기타큐슈 실내로 바로 연결.",
            description = "사라쿠라산 케이블카는 초속 15m 이상 강풍 시 안전을 위해 운휴됩니다. 이 경우 디 아울렛 기타큐슈에서 귀국 전 기념품을 여유롭게 구매하고 실내 천체투영관에서 휴식합니다.",
            indoorAdvantage = "평지 보행로와 안락한 카페 라운지. 부모님 무릎 부담 없이 여유로운 쇼핑.",
            nearbyFoodTip = "디 아울렛 푸드코트: '텐푸라 타카오' (바삭한 즉석 새우·단호박 튀김 정식)"
        ),

        // Day 7
        AlternativeActivity(
            id = "d7_morning_rain_transfer",
            dayNumber = 7,
            originalSpot = "고쿠라 → 하카타 → 후쿠오카공항",
            situation = "우천/교통 지연 시",
            alternativeTitle = "여유 시간 확보 & 특급 소닉·공항선 우선 이용",
            alternativeLocation = "고쿠라역 JR / 하카타역 지하철 공항선",
            transitGuide = "JR 운행 지연 시 특급 소닉 지정석 또는 후속 쾌속을 이용하고, 하카타에서는 지하철 공항선으로 국제선에 직행합니다.",
            description = "10:55 부산행이므로 아침 이동 버퍼를 넉넉히 둡니다. 폭우로 JR이 지연되면 고쿠라역 미도리노마도구치에서 후속편·특급으로 변경하고, 하카타 도착 후 공항선으로 바로 이동합니다.",
            indoorAdvantage = "고쿠라·하카타·공항 모두 실내 환승 동선이 잘 되어 있어 비에 거의 젖지 않습니다.",
            nearbyFoodTip = "공항 국제선 카페에서 간단한 빵·커피로 아침 보충 (출국 전)"
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
            description = "호텔 앞 니시나카지마역을 지나는 미도스지선과 오사카성 노선의 운행 상태를 점검합니다.",
            delayTip = "지하철은 날씨 영향을 거의 받지 않으므로 폭우 시 가장 안전한 이동 수단입니다."
        )
    )

    val weatherGuides = listOf(
        CityWeatherGuide(
            cityName = "오사카 (Osaka)",
            region = "간사이 (9/20 - 9/22 일정)",
            officialUrl = "https://weather.yahoo.co.jp/weather/jp/27/6200.html",
            avgTemp = "22℃ ~ 28℃ (낮에는 쾌적한 가을 날씨)",
            weatherTips = "9월 말 오사카는 낮에는 반팔이나 얇은 셔츠, 저녁에는 얇은 가디건이 적당합니다. 부모님 체온 유지를 위해 얇은 겉옷과 3단 접이식 우산을 가방에 소지하세요.",
            rainyAlternativeSummary = "오사카성 대신 역사박물관, 신세카이 대신 아베노 하루카스 300 실내몰 이용 권장"
        ),
        CityWeatherGuide(
            cityName = "교토 (Kyoto)",
            region = "간사이 (9/21 버스투어 일정)",
            officialUrl = "https://weather.yahoo.co.jp/weather/jp/26/6110.html",
            avgTemp = "20℃ ~ 27℃ (분지 지형으로 아침·저녁 일교차 큼)",
            weatherTips = "교토는 분지 지형이라 비가 오거나 해가 지면 쌀쌀해집니다. 부모님 보온용 스카프나 가디건을 꼭 챙기세요.",
            rainyAlternativeSummary = "우천 시 교토역 이세탄 백화점 및 국립박물관 평지 실내 코스 추천"
        ),
        CityWeatherGuide(
            cityName = "기타큐슈 (고쿠라/모지코)",
            region = "큐슈 북부 (9/23 - 9/26 일정)",
            officialUrl = "https://weather.yahoo.co.jp/weather/jp/40/8220.html",
            avgTemp = "21℃ ~ 27℃ (해안가 바닷바람 강함)",
            weatherTips = "모지코와 간몬 해협은 바닷바람이 불어 체감온도가 낮을 수 있습니다. 사라쿠라산 케이블카 탑승 전 당일 정상 풍속을 확인하세요.",
            rainyAlternativeSummary = "강풍·우천 시 간몬 해저 인도 터널, 큐슈 철도기념관, 디 아울렛 기타큐슈 이용"
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

        // Category 2: 부모님 질환 & 약국/병원/구급차
        EmergencyJapanesePhrase(
            id = 201,
            category = "부모님 질환·약국·응급",
            koreanTitle = "부모님이 어지럽고 혈압/속이 불편하십니다. 가까운 병원이 어디인가요?",
            japaneseText = "親の体調が急に悪くなりました（めまい・腹痛）。近くの内科・総合病院はどこですか？",
            pronunciation = "오야노 타이쵸-가 큐-니 와루쿠 나리마시타 (메마이, 후쿠츠-). 치카쿠노 나이카, 소-고-뵤-인와 도코데스카?",
            situationTip = "호텔 프론트나 역 안내소에 부모님 진료가 가능한 가장 가까운 병원을 물어볼 때 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 202,
            category = "부모님 질환·약국·응급",
            koreanTitle = "소화제(위장약) 또는 어른용 진통제가 있나요?",
            japaneseText = "胃腸薬（太田胃散やキャベジン）、または頭痛鎮痛薬はありますか？",
            pronunciation = "이쵸-야쿠 (오-타이산 야 캬베진), 마타와 즈츠- 진츠-야쿠와 아리마스카?",
            situationTip = "드럭스토어(마츠모토 키요시 등)에서 오타이산이나 카베진 등 상비약을 구매할 때 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 203,
            category = "부모님 질환·약국·응급",
            koreanTitle = "무릎과 허리에 붙이는 온열 파스가 있나요?",
            japaneseText = "膝や腰の痛みに効く湿布（サロンパスなど）はありますか？",
            pronunciation = "히자야 코시노 이타미니 키쿠 십푸 (샤론파스 나도)와 아리마스카?",
            situationTip = "장시간 보행 후 부모님 관절 피로를 풀기 위한 파스를 약국에서 찾을 때 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 204,
            category = "부모님 질환·약국·응급",
            koreanTitle = "긴급 상황입니다! 구급차를 빨리 불러주세요! (119)",
            japaneseText = "緊急事態です！救急車を早く呼んでください！",
            pronunciation = "킨큐- 지타이데스! 큐-큐-샤오 하야쿠 욘데 쿠다사이!",
            situationTip = "부모님께서 급성 통증이나 호흡 곤란 등 응급 처치가 필요할 때 호텔이나 주변 사람에게 즉시 보여주세요."
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

        // Category 4: 식당 안심 주문 (내장 제외 & 신선 회·와규 추천)
        EmergencyJapanesePhrase(
            id = 401,
            category = "식당 주문 (내장 제외·회/와규)",
            koreanTitle = "저희는 내장(호르몬)을 못 먹습니다. 신선한 생선회나 소고기(와규)를 추천해 주세요.",
            japaneseText = "私たちはホルモン（内臓料理）が食べられません。新鮮なお刺身や牛肉（カルビ・ロース）のおすすめはありますか？",
            pronunciation = "와타시타치와 호루몬 (나이조- 료-리)가 타베라레마센. 신센나 오사시미야 규-니쿠 (카루비, 로-스)노 오스스메와 아리마스카?",
            situationTip = "식당 주문 시 질긴 내장(곱창/호르몬/도테야키)을 배제하고 부모님이 좋아하시는 신선한 활어회나 부드러운 와규 살코기 메뉴를 추천받을 때 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 402,
            category = "식당 주문 (내장 제외·회/와규)",
            koreanTitle = "소고기는 부드러운 살코기 부위로 부탁드립니다 (내장 제외).",
            japaneseText = "牛肉はホルモンを除き、柔らかい赤身やロースでお願いします。",
            pronunciation = "규-니쿠와 호루몬오 노조키, 야와라카이 아카미야 로-스데 오네가이시마스.",
            situationTip = "야키니쿠나 고깃집에서 내장 부위를 빼고 연하고 부드러운 소고기 살코기 위주로 주문할 때 보여주세요."
        ),
        EmergencyJapanesePhrase(
            id = 403,
            category = "식당 주문 (내장 제외·회/와규)",
            koreanTitle = "음식이 너무 짜지 않도록 간을 약하게 부탁드립니다.",
            japaneseText = "塩分控えめで、味付けを薄めにお願いできますか？",
            pronunciation = "엔분 히카에메데, 아지츠케오 우수메니 오네가이 데키마스카?",
            situationTip = "부모님의 고혈압 관리나 짠 음식 부담을 줄이기 위해 국물이나 덮밥 간을 조절할 때 유용합니다."
        ),
        EmergencyJapanesePhrase(
            id = 404,
            category = "식당 주문 (내장 제외·회/와규)",
            koreanTitle = "따뜻한 물(또는 따뜻한 녹차)을 부탁드립니다.",
            japaneseText = "温かいお水（または温かいお茶）をいただけますか？",
            pronunciation = "아타타카이 오미즈 (마타와 아타타카이 오차)오 이타다케마스카?",
            situationTip = "얼음물이 기본으로 나오는 일본 식당에서 부모님을 위한 따뜻한 물/차를 요청할 때 필수입니다."
        )
    )
}
