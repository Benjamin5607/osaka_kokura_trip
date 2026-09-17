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
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
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
    val kidMenu: String,
    val price: String,
    val localFeature: String,
    val kidComfortTip: String,
    val addressQuery: String
)

@Composable
fun KidsGourmetScreen() {
    val context = LocalContext.current
    var selectedCity by remember { mutableStateOf("전체") }
    var selectedCategory by remember { mutableStateOf("전체") }

    val spots = remember {
        listOf(
            GourmetSpot(
                id = 1,
                nameKo = "돈카츠 치요 (Chiyo)",
                nameJa = "とんかつ 千代 (西中島)",
                city = "오사카",
                area = "니시나카지마 (호텔 도보 3분)",
                category = "돈카츠",
                kidMenu = "특선 안심(히레) 카츠 정식, 흑돼지 로스카츠",
                price = "1,100엔 ~ 1,600엔",
                localFeature = "현지 회사원과 주민들이 줄 서는 숨은 돈카츠 명점. 한국인 관광객 거의 없음. 젓가락으로 스르륵 잘릴 만큼 부드러운 안심 고기가 일품.",
                kidComfortTip = "아이들이 고기를 씹을 때 질기지 않고 녹아내리듯 연합니다. 달콤한 특제 소스와 밥, 미소된장국 무료 리필.",
                addressQuery = "とんかつ 千代 西中島"
            ),
            GourmetSpot(
                id = 2,
                nameKo = "샌드위치 OCM",
                nameJa = "サンドイッチ OCM (小倉)",
                city = "키타큐슈",
                area = "고쿠라 우오마치 상점가",
                category = "샌드위치",
                kidMenu = "오리지널 에그 샐러드 + 크리스피 치킨 샌드위치, 멜론소다",
                price = "600엔 ~ 900엔",
                localFeature = "1978년 창업 이래 키타큐슈 시민들의 소울푸드! 유명한 시로야 베이커리의 폭신한 갓 구운 식빵에 원하는 재료를 2가지 조합해 즉석에서 만듦.",
                kidComfortTip = "달콤짭조름한 바비큐 치킨과 부드러운 달걀 샐러드 조합은 초등학생들이 가장 열광하는 맛. 매운맛 전무.",
                addressQuery = "サンドイッチ OCM 小倉"
            ),
            GourmetSpot(
                id = 3,
                nameKo = "스케상 우동 (우오마치점)",
                nameJa = "資さんうどん 魚町店",
                city = "키타큐슈",
                area = "고쿠라역 도보 5분 상점가",
                category = "우동",
                kidMenu = "니쿠고보텐(소고기 우엉튀김) 우동, 가츠동, 명물 보타모치(단팥떡)",
                price = "650엔 ~ 950엔",
                localFeature = "키타큐슈를 상징하는 영혼의 우동 체인 본점. 황금빛 천연 다시 육수와 쫄깃한 면발. 현지인 비율 98% 이상.",
                kidComfortTip = "달콤하게 조린 소고기가 듬뿍 들어가 국물이 달짝지근합니다. 갓 구운 따끈한 단팥 보타모치(160엔)는 필수 디저트.",
                addressQuery = "資さんうどん 魚町店"
            ),
            GourmetSpot(
                id = 4,
                nameKo = "베아 후르츠 (BEAR FRUITS)",
                nameJa = "ベアフルーツ 門司港",
                city = "키타큐슈",
                area = "모지코 레트로 해변",
                category = "야키카레",
                kidMenu = "수퍼 야키카레 (치즈 오븐 구이 소고기 카레)",
                price = "1,100엔 ~ 1,400엔",
                localFeature = "일본 여배우 우에토 아야가 인생 맛집으로 극찬한 모지코 원조 야키카레 전문점. 모짜렐라 치즈와 반숙 계란이 오븐에서 지글지글.",
                kidComfortTip = "매운맛 조절 가능 (순한맛 아마구치 주문). 치즈가 카레를 감싸 부드럽고 풍미가 넘쳐 초등학생 취향 저격.",
                addressQuery = "BEAR FRUITS 門司港"
            ),
            GourmetSpot(
                id = 5,
                nameKo = "양식 후지 (Yoshoku Fuji)",
                nameJa = "洋食 富士 (西中島南方)",
                city = "오사카",
                area = "신오사카 써니스톤 호텔 인근",
                category = "함바그·양식",
                kidMenu = "수제 데미글라스 치즈 함박스테이크, 왕새우튀김(에비후라이)",
                price = "1,000엔 ~ 1,500엔",
                localFeature = "일본 쇼와 레트로 감성의 정통 양식당. 두툼한 소고기 패티에서 육즙이 팡 터지며 직접 끓인 깊은 데미글라스 소스가 듬뿍.",
                kidComfortTip = "회/내장 일절 없음. 타르타르 소스 찍어먹는 바삭한 대형 새우튀김과 치즈 함바그로 아이들 밥그릇 싹싹 비움.",
                addressQuery = "洋食 富士 西中島"
            ),
            GourmetSpot(
                id = 6,
                nameKo = "시로야 베이커리 (고쿠라 에키마에)",
                nameJa = "シロヤベーカリー 小倉店",
                city = "키타큐슈",
                area = "JR 고쿠라역 남쪽 출구 바로 앞",
                category = "베이커리",
                kidMenu = "전설의 써니빵(연유빵 140엔), 한입 생크림 오믈렛(50엔)",
                price = "50엔 ~ 150엔",
                localFeature = "키타큐슈에 오면 무조건 들러야 하는 70년 전통 빵집. 현지인들이 박스 채 사가는 전설의 빵.",
                kidComfortTip = "빵을 뜯으면 달콤한 연유가 듬뿍 흘러나옵니다. 아이들 간식으로 최고 가성비.",
                addressQuery = "シロヤベーカリー 小倉店"
            ),
            GourmetSpot(
                id = 7,
                nameKo = "쿠시카츠 텐구 (天狗)",
                nameJa = "てんぐ 新世界",
                city = "오사카",
                area = "신세카이 잔잔요코초",
                category = "쿠시카츠",
                kidMenu = "소고기 규카츠 꼬치, 모짜렐라 치즈, 비엔나 소시지, 메추리알, 옥수수 튀김",
                price = "150엔 ~ 250엔 (꼬치당)",
                localFeature = "한국인 패키지 관광객이 몰리는 다루마 대신 현지인들이 골목에서 줄 서는 얇고 바삭한 쿠시카츠 명가.",
                kidComfortTip = "★내장(도테야키/호르몬)은 일절 주문하지 않고, 아이들이 좋아하는 소고기/치즈/소시지/감자 튀김만 쏙쏙 골라 주문하면 완벽!",
                addressQuery = "てんぐ 新世界"
            ),
            GourmetSpot(
                id = 8,
                nameKo = "사누키 우동 카메이 (Kamei)",
                nameJa = "讃岐うどん 亀井 (新大阪)",
                city = "오사카",
                area = "신오사카역 / 온천 인근",
                category = "우동",
                kidMenu = "바삭한 닭튀김(도리텐) 온우동, 달콤 키츠네 유부우동",
                price = "700엔 ~ 950엔",
                localFeature = "매일 아침 직접 반죽하여 족타로 치대는 탱글탱글한 수제 사누키 면발. 가쓰오와 다시마로 맑게 우려낸 육수.",
                kidComfortTip = "달콤하게 졸인 큼직한 유부와 바삭한 순살 닭튀김은 자극적이지 않고 영양 만점입니다.",
                addressQuery = "讃岐うどん 亀井 新大阪"
            ),
            GourmetSpot(
                id = 9,
                nameKo = "아지요시 오코노미야키",
                nameJa = "味のれん 難波",
                city = "오사카",
                area = "난바 센니치마에 상점가",
                category = "오코노미야키",
                kidMenu = "돼지고기 삼겹살 부타타마, 야키소바, 계란말이 톤페이야키",
                price = "900엔 ~ 1,300엔",
                localFeature = "테이블 철판에서 바로 구워주는 정통 오사카식 부침개. 양배추의 달콤함과 바삭한 삼겹살이 조화로움.",
                kidComfortTip = "해산물이나 내장 없이 순수 돼지고기와 계란, 양배추로만 부쳐내어 아이들이 부담 없이 달콤한 소스와 함께 즐길 수 있습니다.",
                addressQuery = "味のれん 難波 お好み焼き"
            ),
            GourmetSpot(
                id = 10,
                nameKo = "오야코동 명가 히사고 (Hisago)",
                nameJa = "ひさご 京都 祇園",
                city = "교토",
                area = "교토 기요미즈데라 인근",
                category = "함바그·양식",
                kidMenu = "교토 특제 오야코동 (달콤한 닭고기 달걀 덮밥), 키츠네 우동",
                price = "1,100엔 ~ 1,400엔",
                localFeature = "70년 넘게 사랑받는 교토의 대표 닭고기 달걀덮밥. 반숙 달걀의 크리미함과 특제 간장 육수의 감칠맛.",
                kidComfortTip = "날생선/내장 전혀 없이 푹 익힌 닭다리살과 달걀의 부드러움이 일품. 아이들이 한 그릇 뚝딱 비웁니다.",
                addressQuery = "ひさご 京都 祇園"
            ),
            GourmetSpot(
                id = 11,
                nameKo = "쿠루메 타이호 라멘 (고쿠라)",
                nameJa = "大砲ラーメン 小倉店",
                city = "키타큐슈",
                area = "고쿠라역 인근",
                category = "라멘",
                kidMenu = "기본 마일드 차슈멘 (돼지고기 편육 라멘), 히토구치 한입 교자",
                price = "750엔 ~ 1,000엔",
                localFeature = "60년 전통 '요비모도시' 기법으로 끓여낸 깊고 고소한 돈코츠 육수. 잡내가 없어 현지 가족 단위 손님이 많음.",
                kidComfortTip = "초등학생 입맛에 딱 맞게 짜지 않은 마일드 차슈멘 추천. 얇고 바삭한 한입 군만두는 필수 짝꿍.",
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
            .testTag("kids_gourmet_screen"),
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
                        text = "100% 안심 보증: 회 & 내장 요리 완전 제외",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF065F46)
                        )
                    )
                    Text(
                        text = "초등학생 아이들이 환호하는 일본인들의 현지 찐 맛집",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF047857),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            val items = listOf(
                "날생선(스시/사시미) 및 호르몬/내장(곱창/도테야키) 메뉴 원천 배제",
                "바삭한 돈카츠, 달콤한 함박스테이크, 쫄깃한 소고기 우동, 치즈 오븐 야키카레 중심",
                "한국인 블로그 유명 관광식당 탈피, 일본 로컬 주민들의 타베로그 인기 맛집 엄선"
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
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onSelectCity(city) }
                    .testTag("gourmet_city_$city")
            ) {
                Text(
                    text = city,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun CategoryFilterRow(selectedCategory: String, onSelectCategory: (String) -> Unit) {
    val categories = listOf("전체", "돈카츠", "우동", "함바그·양식", "야키카레", "오코노미야키", "샌드위치", "베이커리", "라멘")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        categories.forEach { cat ->
            val isSelected = selectedCategory == cat
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.outlineVariant),
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onSelectCategory(cat) }
            ) {
                Text(
                    text = cat,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
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

            // Recommended Kid Menu
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
                            text = "추천 키즈 메뉴 (안심 주문)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC2410C)
                            )
                        )
                        Text(
                            text = spot.kidMenu,
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

            // Child Comfort Tip
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
                    Icon(Icons.Default.ChildCare, contentDescription = null, tint = EmeraldSafe, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = spot.kidComfortTip,
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
