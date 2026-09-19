package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsSubway
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberWarm
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.IndigoPrimary

@Composable
fun TransitPassScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("transit_pass_screen"),
        contentPadding = PaddingValues(16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TransitHeaderCard()
        }

        // Section: Osaka Metro Pass 2-Day & Day 3 Visa / 1-Day Pass Strategy
        item {
            OsakaMetroStrategyCard()
        }

        // Section 1: Kansai Airport -> Shin-Osaka
        item {
            TransitDetailCard(
                badgeText = "구간 1",
                badgeColor = IndigoPrimary,
                icon = Icons.Default.Train,
                title = "간사이 공항(KIX) -> 신오사카 써니스톤 호텔",
                routeSummary = "JR 특급 하루카 (환승 없이 직통 50분 소요)",
                steps = listOf(
                    "간사이 공항 입국장(1층) 나와 에스컬레이터로 2층 철도역 이동",
                    "JR 개찰구 앞 매표기 또는 티켓 오피스에서 '하루카 승차권' 발권",
                    "★교통카드 팁: ICOCA 카드를 구매해 충전해두면 지하철, 편의점, 자판기에서 잔돈 없이 원터치 결제가 가능하여 부모님과 이동 시 매우 편리합니다.",
                    "JR 4번 승강장에서 '특급 하루카(헬로키티 열차)' 탑승 -> 신오사카역 하차",
                    "신오사카역 도착 후 남쪽 출구에서 도보 8분, 또는 미도스지선(빨간선) 탑승 1정거장 '니시나카지마미나미가타역' 2번 출구 도보 1분이면 써니스톤 호텔 도착!"
                ),
                tip = "무거운 캐리어를 끌고 이동할 때는 니시나카지마미나미가타역 2번 출구 엘리베이터를 이용하시면 계단 없이 호텔 정문까지 평지로 이동할 수 있습니다."
            )
        }

        // Section 2: Shin-Osaka -> Kokura Shinkansen
        item {
            TransitDetailCard(
                badgeText = "구간 2",
                badgeColor = CrimsonAccent,
                icon = Icons.Default.DirectionsTransit,
                title = "신오사카역 -> 고쿠라역 (산요 신칸센)",
                routeSummary = "산요 신칸센 노조미/사쿠라 (약 2시간 10분, 11시 도착)",
                steps = listOf(
                    "아침 08:30경 신오사카역 신칸센 전용 개찰구로 이동",
                    "08:50 전후 출발 노조미(Nozomi) 또는 사쿠라(Sakura) 열차 탑승",
                    "열차 내 정갈한 에키벤(일본 명물 기차 도시락)과 따뜻한 차를 즐기며 쾌적하게 이동",
                    "11:00 고쿠라역(Kokura) 도착",
                    "★호텔 직결 꿀팁: 고쿠라역 개찰구를 나와 역사 건물 내 엘리베이터를 타고 7층으로 올라가면 'JR큐슈 스테이션 호텔 고쿠라' 프론트와 바로 연결됩니다! (밖으로 나갈 필요 Zero)"
                ),
                tip = "세 변의 합이 160cm를 초과하는 대형 캐리어는 신칸센 예약 시 '특대수하물 좌석(맨 뒷자리)'을 사전에 무료 지정해야 합니다. 일반 24~26인치 캐리어는 좌석 앞 무릎 공간이나 상단 선반에 충분히 들어갑니다."
            )
        }

        // Section 3: Kitakyushu Sightseeing Pass
        item {
            KitakyushuPassOverviewCard()
        }

        // Section 4: Kanmon Undersea Pedestrian Tunnel
        item {
            TransitDetailCard(
                badgeText = "특별 체험",
                badgeColor = Color(0xFF0D9488),
                icon = Icons.Default.Route,
                title = "간몬 해저인도 터널 (규슈 <-> 혼슈 도보 횡단)",
                routeSummary = "해저 55미터 속 780m를 걷는 바다 밑 모험",
                steps = listOf(
                    "모지코역에서 주유패스로 시내버스 탑승 -> '간몬 터널 인도구찌' 하차",
                    "전용 엘리베이터를 타고 지하 55m 해저로 하강",
                    "해저 780m 터널을 부모님과 함께 평지로 산책 (약 15분 도보, 계단 없이 전 구간 평지)",
                    "터널 한가운데 '후쿠오카현(福岡県) - 야마구치현(山口県)' 경계선에서 기념촬영",
                    "시모노세키 출구로 나와 스탬프 랠리 완성 -> 카라토항에서 간몬 연락선(도선 페리, 5분 소요, 주유패스 무료)을 타고 시원한 바다를 건너 모지코로 귀환"
                ),
                tip = "보행자는 완전 무료! 시모노세키와 모지코 양쪽 엘리베이터 홀에 비치된 기념 엽서에 도장을 반씩 찍어 합체하는 '간몬 횡단 기념 증명서'는 가족 여행의 소중한 기념품이 됩니다."
            )
        }

        // Section 5: Sarakurayama Cable Car
        item {
            TransitDetailCard(
                badgeText = "야경 명소",
                badgeColor = Color(0xFF7C3AED),
                icon = Icons.Default.Landscape,
                title = "사라쿠라산(皿倉山) 케이블카 & 슬로프카",
                routeSummary = "신일본 3대 야경 (100억 달러의 파노라마 야경)",
                steps = listOf(
                    "고쿠라역에서 JR 가고시마 본선 탑승 -> '야하타역(八幡駅, 12분)' 하차",
                    "야하타역 출구 앞에서 주유패스 무료 셔틀버스 탑승 -> 케이블카 산록역 도착",
                    "유리창으로 탁 트인 케이블카 탑승 후 슬로프카로 환승하여 정상 전망대 도착",
                    "18:30~19:30 일몰 매직아워 감상 후 역순으로 귀환"
                ),
                tip = "산 정상은 바닷바람으로 쌀쌀할 수 있으니 부모님을 위한 따뜻한 겉옷을 꼭 챙기세요. 정상 전망대 실내 카페테리아에서 따뜻한 차를 마시며 야경을 편안하게 조망할 수 있습니다."
            )
        }
    }
}

@Composable
fun TransitHeaderCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = IndigoPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ConfirmationNumber,
                    contentDescription = null,
                    tint = Color(0xFFFDE68A),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "스마트 교통 & 패스 완벽 가이드",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "오사카 간사이공항 입국부터 산요 신칸센 고속 이동, 키타큐슈 주유 패스까지 부모님 동반 가족을 위한 최적 동선과 환승 꿀팁을 한눈에 확인하세요.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFE2E8F0),
                    lineHeight = 20.sp
                )
            )
        }
    }
}

@Composable
fun KitakyushuPassOverviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
        border = BorderStroke(1.dp, Color(0xFFFDE68A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(AmberWarm)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "핵심 패스",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "키타큐슈 주유 패스 (웰컴 패스) 이용 범위",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F)
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            val benefits = listOf(
                Pair("JR 재래선 무제한", "고쿠라역 ↔ 모지코역, 스페이스월드역, 야하타역, 오리오역 등 주요 역 자유 승하차"),
                Pair("니시테츠 시내버스", "고쿠라 시내 전역, 고쿠라성, 탄가시장, 차차타운행 버스 탑승"),
                Pair("간몬 기선 연락선", "모지코항 ↔ 시모노세키 카라토항 왕복 페리 무료 이용"),
                Pair("사라쿠라산 셔틀", "야하타역 ↔ 사라쿠라산 케이블카 승강장 왕복 셔틀버스"),
                Pair("관광지 입장료 할인", "규슈 철도기념관, 고쿠라성 및 정원 할인 혜택")
            )

            benefits.forEach { (title, desc) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = AmberWarm,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF78350F)
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransitDetailCard(
    badgeText: String,
    badgeColor: Color,
    icon: ImageVector,
    title: String,
    routeSummary: String,
    steps: List<String>,
    tip: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(badgeColor)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = badgeText,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(icon, contentDescription = null, tint = badgeColor, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "주요 이동: $routeSummary",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Step List
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(badgeColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${index + 1}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = badgeColor
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp
                        )
                    )
                }
            }

            // Tip Box
            if (tip.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = AmberContainer,
                    border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = AmberWarm,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = tip,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF78350F),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OsakaMetroStrategyCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.5.dp, Color(0xFF2563EB).copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF059669)
                ) {
                    Text(
                        text = "Klook 구매완료",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "오사카 메트로패스 2일권 & 3일차 전략",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B)
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "예약 번호: ZNZ343191 | 지하철 9개 노선 무제한 & 비접촉 터치 탑승",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Part 1: Metro Pass 2-Day (Day 1 & Day 2)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFEFF6FF),
                border = BorderStroke(1.dp, Color(0xFFBFDBFE)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.DirectionsSubway,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "1~2일차 (9/20 일 ~ 9/21 월): 메트로패스 2일권 무제한",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E40AF)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• 적용 구간: 오사카 지하철(미도스지선, 주오선, 사카이스지선 등) 9개 노선 전체 무제한\n" +
                                "• 1일차 동선: 공항 도착 후 신오사카(숙소) ↔ 오사카성(혼마치 환승) ↔ 신세카이/츠텐카쿠 왕복\n" +
                                "• 2일차 동선: 숙소(니시나카지마) ↔ 난바(Klook 교토 버스투어 집결지) 및 도톤보리 저녁 왕복\n" +
                                "• 실물권 수령: 간사이 공항 또는 주요 지하철역(우메다, 난바 등) 전용 키오스크에서 모바일 바우처 QR코드를 스캔하여 실물 마그네틱 티켓으로 즉시 교환\n" +
                                "• 개찰구 통과: 개찰구 승차권 투입구에 넣고 통과 (첫 사용 시 티켓 뒷면에 개시일 인쇄)",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF334155),
                            lineHeight = 18.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Part 2: Day 3 Strategy (Visa Contactless vs 1-Day Pass)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF8FAFC),
                border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = CrimsonAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "3일차 (9/22 화): 시내 쇼핑 & 온천 교통 최적 선택",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF991B1B)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Option A: Visa Contactless
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFF1E3A8A)
                                ) {
                                    Text(
                                        text = "추천 ① 비자(VISA) 컨택리스 터치 탑승",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• 오사카 메트로 전 역 개찰구에 비접촉 전파 마크(와이파이 모양) 결제 단말기 설치 완료\n" +
                                        "• 별도 승차권 발권 없이, 소지하신 해외 비자/마스터 실물 카드나 애플페이/삼성페이를 단말기에 '띡(Tap & Go)' 터치하여 바로 통과!\n" +
                                        "• 3일차는 니시나카지마 ↔ 우메다(190엔) 1~2회 위주 이동이므로, 발권 줄 설 필요 없이 비자 터치가 가장 편리하고 저렴합니다.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF334155),
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Option B: 1-Day Pass (Enjoy Eco Card)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White,
                        border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFD97706)
                                ) {
                                    Text(
                                        text = "선택 ② 오사카 메트로 1일권 (엔조이 에코 카드)",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• 지하철역 무인 발권기에서 당일 구매 가능 (평일 성인 820엔)\n" +
                                        "• 3일차에 우메다 쇼핑 후 난바, 텐노지 등 시내 지하철을 4회 이상 자주 탈 계획이라면 1일권이 본전 이상으로 유리합니다.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF334155),
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
