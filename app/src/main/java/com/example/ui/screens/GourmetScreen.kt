package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberWarm
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.IndigoPrimary

data class GourmetSpot(
    val id: Int,
    val nameKo: String,
    val nameJa: String,
    val city: String,
    val area: String,
    val category: String,
    val recommendedMenu: String,
    val price: String,
    val localFeature: String,
    val comfortTip: String,
    val addressQuery: String
)

@Composable
fun GourmetScreen() {
    val context = LocalContext.current
    var selectedCity by remember { mutableStateOf("전체") }
    var selectedCategory by remember { mutableStateOf("전체") }

    val spots = remember {
        listOf(
            GourmetSpot(
                id = 101,
                nameKo = "스시 사카바 사시스 (Sashisu)",
                nameJa = "すし酒場 さしす (なんば・うめだ)",
                city = "오사카",
                area = "난바 센니치마에 / 우메다 제3빌딩",
                category = "회·스시",
                recommendedMenu = "혼마구로 참치 사시미, 모둠 활어 생선회 3종, 도로철화마키(참치 듬뿍 김말이), 도미·방어 초밥",
                price = "사시미 580엔~ / 초밥 2관 300엔~ (1인 2,000엔대)",
                localFeature = "오사카 현지인들이 번호표를 뽑고 줄 서는 압도적 가성비 1등 스시·사시미 주점. 두툼하고 기름진 참치 뱃살과 당일 갓 잡은 신선한 활어회를 시장 가격 그대로 제공.",
                comfortTip = "★부모님 극찬: 회를 좋아하시는 부모님께서 '이 가격에 이 퀄리티라니!' 하고 감탄하시는 가성비 끝판왕. 내장 요리는 전혀 없으며, 매장 내부가 깔끔하고 생선 선도가 극상입니다.",
                addressQuery = "すし酒場 さしす なんば"
            ),
            GourmetSpot(
                id = 102,
                nameKo = "사카에스시 (Sakae Sushi)",
                nameJa = "さかえすし (道頓堀・東心斎橋)",
                city = "오사카",
                area = "도톤보리 도보 3분 골목",
                category = "회·스시",
                recommendedMenu = "신선 모둠 생선회(특선 사시미), 도미 머리 소금구이, 활어초밥(광어, 연어, 단새우, 우니)",
                price = "초밥 1관 100엔~350엔 / 모둠회 1,200엔~",
                localFeature = "도톤보리 뒷골목에서 수십 년간 로컬 단골들의 사랑을 받는 가성비 활어 초밥 & 횟집 노포. 장인이 눈앞에서 정성껏 회를 썰어 쥠.",
                comfortTip = "가성비가 뛰어나 부모님께서 드시고 싶은 회와 초밥을 종류별로 부담 없이 마음껏 주문해 드릴 수 있습니다. (주문표에 한국어 병기)",
                addressQuery = "さかえすし 東心斎橋"
            ),
            GourmetSpot(
                id = 103,
                nameKo = "우오야 피에로 (Fishmonger Pierrot)",
                nameJa = "魚屋 ぴえろ (梅田駅前第3ビル)",
                city = "오사카",
                area = "우메다 제3빌딩 지하 2층",
                category = "회·스시",
                recommendedMenu = "점심 특선 활어 사시미 7종 모둠 정식, 생연어·혼마구로 카이센동(해산물 덮밥)",
                price = "점심 정식 1,100엔 ~ 1,600엔",
                localFeature = "오사카 중앙도매시장 직송 신선 해산물만 취급하는 우메다 직장인들의 숨은 성지. 밥 위에 회를 산더미처럼 얹어주는 미친 가성비.",
                comfortTip = "우메다 백화점 쇼핑 후 에어컨 나오는 지하통로로 바로 연결됩니다. 맑은 조개 미소된장국과 함께 나오는 정갈한 사시미 한 상.",
                addressQuery = "魚屋 ぴえろ 梅田"
            ),
            GourmetSpot(
                id = 104,
                nameKo = "야키니쿠 이치 (Yakiniku Ichi)",
                nameJa = "黒毛和牛 焼肉 一 (心斎橋・難波)",
                city = "오사카",
                area = "신사이바시 / 도톤보리 인근",
                category = "와규·고기",
                recommendedMenu = "흑모와규 특선 상갈비(상카루비), 부드러운 안심(로스), 꽃등심 구이 (내장 제외)",
                price = "1인 3,500엔 ~ 5,000엔",
                localFeature = "일본 전국에서 엄선한 흑모와규(A4~A5 등급) 암소를 통째로 매입하여 최고급 부위를 놀라운 가성비로 내놓는 정육 직영점.",
                comfortTip = "★부모님 안심 와규: 질기거나 냄새나는 내장(호르몬) 부위를 일절 배제하고, 부모님 치아에 부드럽게 녹아내리는 살코기 갈비와 로스 위주로 주문 가능. 연기 없는 최신 로스터 완비.",
                addressQuery = "焼肉 一 心斎橋店"
            ),
            GourmetSpot(
                id = 105,
                nameKo = "하리쥬 (Hariju) 도톤보리 본점",
                nameJa = "播重 道頓堀本店 (すき焼き)",
                city = "오사카",
                area = "도톤보리 입구",
                category = "와규·고기",
                recommendedMenu = "전통 간사이식 흑모와규 스키야키 정식, 소고기 샤브샤브",
                price = "점심 4,000엔~ / 저녁 7,000엔~",
                localFeature = "1919년 창업, 100년 넘는 역사를 지닌 오사카 와규 정육점의 전설. 전통 일본식 가옥과 극상의 흑모와규 마블링.",
                comfortTip = "부모님을 모시고 가는 효도 식사의 정점! 직원분이 무쇠 냄비에 최상급 와규와 채소를 직접 알맞게 익혀주어 부모님께서 매우 편안하고 대접받는 기분을 느끼십니다.",
                addressQuery = "播重 道頓堀本店"
            ),
            GourmetSpot(
                id = 1,
                nameKo = "돈카츠 치요 (Chiyo)",
                nameJa = "とんかつ 千代 (西中島)",
                city = "오사카",
                area = "니시나카지마 (호텔 도보 3분)",
                category = "돈카츠",
                recommendedMenu = "특선 안심(히레) 카츠 정식, 부드러운 로스카츠 정식",
                price = "1,100엔 ~ 1,600엔",
                localFeature = "현지 회사원과 주민들이 줄 서는 숨은 돈카츠 명점. 한국인 관광객 거의 없음. 젓가락으로 스르륵 잘릴 만큼 부드러운 안심 고기가 일품.",
                comfortTip = "부모님 치아에 전혀 부담 없을 정도로 안심이 부드럽고 연합니다. 자극적이지 않은 맑은 장국과 갓 지은 밥이 함께 나옵니다.",
                addressQuery = "とんかつ 千代 西中島"
            ),
            GourmetSpot(
                id = 2,
                nameKo = "샌드위치 OCM",
                nameJa = "サンドイッチ OCM (小倉)",
                city = "키타큐슈",
                area = "고쿠라 우오마치 상점가",
                category = "샌드위치",
                recommendedMenu = "오리지널 에그 샐러드 + 촉촉한 치킨 샌드위치, 따뜻한 드립 커피",
                price = "600엔 ~ 900엔",
                localFeature = "1978년 창업 이래 키타큐슈 시민들의 소울푸드! 유명한 시로야 베이커리의 폭신한 갓 구운 식빵에 원하는 재료를 2가지 조합해 즉석에서 만듦.",
                comfortTip = "부드러운 달걀 샐러드와 담백한 치킨의 정갈한 조화. 상점가 아케이드 내에 있어 부모님과 편안히 쉬어가기 좋습니다.",
                addressQuery = "サンドイッチ OCM 小倉"
            ),
            GourmetSpot(
                id = 3,
                nameKo = "스케상 우동 (우오마치점)",
                nameJa = "資さんうどん 魚町店",
                city = "키타큐슈",
                area = "고쿠라역 도보 5분 상점가",
                category = "우동",
                recommendedMenu = "니쿠고보텐(소고기 우엉튀김) 온우동, 명물 보타모치(단팥떡)",
                price = "650엔 ~ 950엔",
                localFeature = "키타큐슈를 상징하는 영혼의 우동 체인 본점. 황금빛 천연 다시 육수와 쫄깃한 면발. 현지인 비율 98% 이상.",
                comfortTip = "자극적이지 않고 구수한 천연 다시 육수에 달콤하게 조린 소고기가 듬뿍 들어가 부모님 입맛에 딱 맞습니다. 갓 빚은 따끈한 단팥 보타모치는 필수 디저트.",
                addressQuery = "資さんうどん 魚町店"
            ),
            GourmetSpot(
                id = 4,
                nameKo = "베아 후르츠 (BEAR FRUITS)",
                nameJa = "ベアフルーツ 門司港",
                city = "키타큐슈",
                area = "모지코 레트로 해변",
                category = "야키카레",
                recommendedMenu = "수퍼 야키카레 (치즈 오븐 구이 소고기 카레, 맵지 않은 순한맛)",
                price = "1,100엔 ~ 1,400엔",
                localFeature = "일본 여배우 우에토 아야가 인생 맛집으로 극찬한 모지코 원조 야키카레 전문점. 모짜렐라 치즈와 반숙 계란이 오븐에서 지글지글.",
                comfortTip = "매운맛을 순하게 조절 가능(아마구치 주문)하며 풍부한 치즈와 부드러운 카레가 속을 편안하게 해줍니다. 회/내장 일절 배제.",
                addressQuery = "BEAR FRUITS 門司港"
            ),
            GourmetSpot(
                id = 5,
                nameKo = "양식 후지 (Yoshoku Fuji)",
                nameJa = "洋食 富士 (西中島南方)",
                city = "오사카",
                area = "신오사카 써니스톤 호텔 인근",
                category = "함바그·양식",
                recommendedMenu = "수제 데미글라스 치즈 함박스테이크, 바삭한 왕새우튀김(에비후라이)",
                price = "1,000엔 ~ 1,500엔",
                localFeature = "일본 쇼와 레트로 감성의 정통 양식당. 두툼한 소고기 패티에서 육즙이 팡 터지며 직접 끓인 깊은 데미글라스 소스가 듬뿍.",
                comfortTip = "회/내장 일절 없음. 부드러운 수제 함바그라 씹기 편하고, 타르타르 소스 찍어먹는 고소한 왕새우튀김으로 부모님 식사 만족도 최상.",
                addressQuery = "洋食 富士 西中島"
            ),
            GourmetSpot(
                id = 6,
                nameKo = "시로야 베이커리 (고쿠라 에키마에)",
                nameJa = "シロヤベーカリー 小倉店",
                city = "키타큐슈",
                area = "JR 고쿠라역 남쪽 출구 바로 앞",
                category = "베이커리",
                recommendedMenu = "전설의 써니빵(연유빵 140엔), 한입 생크림 오믈렛(50엔)",
                price = "50엔 ~ 150엔",
                localFeature = "키타큐슈에 오면 무조건 들러야 하는 70년 전통 빵집. 현지인들이 박스 채 사가는 전설의 빵.",
                comfortTip = "빵을 뜯으면 달콤한 연유가 흘러나오며 빵 식감이 촉촉하고 부드럽습니다. 이동 중 부모님 간식이나 호텔 야식으로 안성맞춤.",
                addressQuery = "シロヤベーカリー 小倉店"
            ),
            GourmetSpot(
                id = 7,
                nameKo = "쿠시카츠 텐구 (天狗)",
                nameJa = "てんぐ 新世界",
                city = "오사카",
                area = "신세카이 잔잔요코초",
                category = "쿠시카츠",
                recommendedMenu = "소고기 규카츠 꼬치, 아스파라거스, 표고버섯, 연근, 새우 튀김",
                price = "150엔 ~ 250엔 (꼬치당)",
                localFeature = "한국인 패키지 관광객이 몰리는 대형 체인 대신 현지인들이 골목에서 줄 서는 얇고 바삭한 쿠시카츠 명가.",
                comfortTip = "★내장(도테야키/호르몬)은 일절 주문하지 않고, 소고기 살코기와 신선한 채소 튀김 위주로 깔끔하게 즐기시면 기름지지 않고 담백합니다.",
                addressQuery = "てんぐ 新世界"
            ),
            GourmetSpot(
                id = 8,
                nameKo = "사누키 우동 카메이 (Kamei)",
                nameJa = "讃岐うどん 亀井 (新大阪)",
                city = "오사카",
                area = "신오사카역 / 온천 인근",
                category = "우동",
                recommendedMenu = "바삭한 닭튀김(도리텐) 온우동, 달콤 키츠네 유부우동",
                price = "700엔 ~ 950엔",
                localFeature = "매일 아침 직접 반죽하여 족타로 치대는 탱글탱글한 수제 사누키 면발. 가쓰오와 다시마로 맑게 우려낸 육수.",
                comfortTip = "짜지 않고 담백하고 맑은 천연 육수로 부모님 속이 편안합니다. 큼직하고 부드러운 유부와 갓 튀긴 닭고기 튀김이 일품.",
                addressQuery = "讃岐うどん 亀井 新大阪"
            ),
            GourmetSpot(
                id = 9,
                nameKo = "아지요시 오코노미야키",
                nameJa = "味のれん 難波",
                city = "오사카",
                area = "난바 센니치마에 상점가",
                category = "오코노미야키",
                recommendedMenu = "돼지고기 삼겹살 부타타마, 야키소바, 계란말이 톤페이야키",
                price = "900엔 ~ 1,300엔",
                localFeature = "테이블 철판에서 바로 구워주는 정통 오사카식 부침개. 양배추의 달콤함과 바삭한 삼겹살이 조화로움.",
                comfortTip = "해산물이나 내장 없이 순수 돼지고기와 계란, 양배추로만 부쳐내어 부모님께서 부담 없이 고소한 맛을 즐기실 수 있습니다.",
                addressQuery = "味のれん 難波 お好み焼き"
            ),
            GourmetSpot(
                id = 10,
                nameKo = "오야코동 명가 히사고 (Hisago)",
                nameJa = "ひさご 京都 祇園",
                city = "교토",
                area = "교토 기요미즈데라 인근",
                category = "함바그·양식",
                recommendedMenu = "교토 특제 오야코동 (달콤 부드러운 닭고기 달걀 덮밥), 키츠네 온우동",
                price = "1,100엔 ~ 1,400엔",
                localFeature = "70년 넘게 사랑받는 교토의 대표 닭고기 달걀덮밥. 반숙 달걀의 크리미함과 특제 간장 육수의 감칠맛.",
                comfortTip = "날생선/내장 전혀 없이 푹 익힌 부드러운 닭다리살과 달걀의 조화. 부모님께서 소화하기 가장 편안하고 정갈한 한 끼.",
                addressQuery = "ひさご 京都 祇園"
            ),
            GourmetSpot(
                id = 11,
                nameKo = "쿠루메 타이호 라멘 (고쿠라)",
                nameJa = "大砲ラーメン 小倉店",
                city = "키타큐슈",
                area = "고쿠라역 인근",
                category = "라멘",
                recommendedMenu = "기본 마일드 차슈멘 (돼지고기 편육 라멘), 바삭한 한입 교자(군만두)",
                price = "750엔 ~ 1,000엔",
                localFeature = "60년 전통 '요비모도시' 기법으로 끓여낸 깊고 고소한 돈코츠 육수. 잡내가 없어 현지 가족 손님이 많음.",
                comfortTip = "기름지지 않고 구수한 마일드 차슈멘 추천. 얇고 바삭한 한입 군만두와 함께 드시면 부모님 입맛에도 잘 맞습니다.",
                addressQuery = "大砲ラーメン 小倉"
            )
        )
    }

    val filteredSpots = spots.filter { spot ->
        val cityMatch = if (selectedCity == "전체") true else spot.city == selectedCity
        val categoryMatch = if (selectedCategory == "전체") true else spot.category == selectedCategory
        cityMatch && categoryMatch
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("gourmet_screen"),
        contentPadding = PaddingValues(16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Guarantee Header Banner
        item {
            GourmetGuaranteeBanner()
        }

        // Filter chips: City
        item {
            CityFilterRow(
                selectedCity = selectedCity,
                onSelectCity = { selectedCity = it }
            )
        }

        // Filter chips: Category
        item {
            CategoryFilterRow(
                selectedCategory = selectedCategory,
                onSelectCategory = { selectedCategory = it }
            )
        }

        // Restaurant cards
        items(filteredSpots, key = { it.id }) { spot ->
            GourmetSpotCard(
                spot = spot,
                onOpenMap = { openGoogleMapsForSpot(context, spot.addressQuery) }
            )
        }
    }
}

@Composable
fun GourmetGuaranteeBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = EmeraldContainer),
        border = BorderStroke(1.5.dp, Color(0xFF34D399)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(EmeraldSafe),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "부모님 최애: 신선 활어회·스시 & 가성비 흑모와규",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF065F46)
                        )
                    )
                    Text(
                        text = "100% 안심 보증: 질긴 내장(호르몬/도테야키/곱창)만 완전 제외",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF047857),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            val items = listOf(
                "★부모님 극찬: 신선한 참치 뱃살·도미 사시미 & 시장 직송 가성비 활어초밥 엄선",
                "★가성비 와규: 입에서 사르르 녹는 최상급 흑모와규 갈비·로스 & 전통 스키야키 완비",
                "★철저 배제: 질기거나 냄새나는 내장(소곱창/대창/호르몬/도테야키) 메뉴는 일절 제외",
                "한국인 블로그 유명 식당 탈피, 현지인들이 번호표 뽑는 로컬 가성비 찐 맛집"
            )
            items.forEach { text ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = EmeraldSafe, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF064E3B),
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CityFilterRow(selectedCity: String, onSelectCity: (String) -> Unit) {
    val cities = listOf("전체", "오사카", "키타큐슈", "교토")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        cities.forEach { city ->
            val isSelected = selectedCity == city
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onSelectCity(city) }
            ) {
                Text(
                    text = city,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun CategoryFilterRow(selectedCategory: String, onSelectCategory: (String) -> Unit) {
    val categories = listOf("전체", "회·스시", "와규·고기", "돈카츠", "우동", "야키카레", "함바그·양식", "오코노미야키", "라멘", "샌드위치", "베이커리", "쿠시카츠")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        categories.forEach { cat ->
            val isSelected = selectedCategory == cat
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) AmberWarm else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSelectCategory(cat) }
            ) {
                Text(
                    text = cat,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Composable
fun GourmetSpotCard(spot: GourmetSpot, onOpenMap: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("gourmet_card_${spot.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: City, Category, Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(IndigoPrimary)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = spot.city,
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = spot.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
                Text(
                    text = spot.price,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = AmberWarm,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Name
            Text(
                text = spot.nameKo,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = spot.nameJa,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )

            // Location & Map button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = AmberWarm, modifier = Modifier.size(15.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = spot.area,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.weight(1f)
                )
                TextButton(
                    onClick = onOpenMap,
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) {
                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("위치 보기", fontSize = 12.sp)
                }
            }

            // Recommended Menu
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFFF7ED),
                border = BorderStroke(1.dp, Color(0xFFFFEDD5)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Restaurant, contentDescription = null, tint = Color(0xFFEA580C), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "추천 안심 메뉴 (부모님 추천)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC2410C)
                            )
                        )
                        Text(
                            text = spot.recommendedMenu,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF9A3412),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            // Local Japanese Feature
            Text(
                text = "현지 특징: ${spot.localFeature}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 18.sp
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Senior Comfort Tip
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF0FDF4),
                border = BorderStroke(1.dp, Color(0xFFDCFCE7)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Default.Security, contentDescription = null, tint = EmeraldSafe, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = spot.comfortTip,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF166534),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

private fun openGoogleMapsForSpot(context: Context, query: String) {
    try {
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Uri.encode(query)))
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query)))
            context.startActivity(webIntent)
        }
    } catch (e: Exception) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + Uri.encode(query)))
        context.startActivity(browserIntent)
    }
}
