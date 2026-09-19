package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
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
import com.example.data.AlternativeActivity
import com.example.data.CityWeatherGuide
import com.example.data.ContingencyData
import com.example.data.EmergencyContactEntity
import com.example.data.EmergencyJapanesePhrase
import com.example.data.RealtimeTrafficLink
import com.example.ui.TripUiState
import com.example.ui.TripViewModel
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberWarm
import com.example.ui.theme.CatTransit
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.IndigoPrimary

@Composable
fun ContingencyScreen(
    viewModel: TripViewModel,
    uiState: TripUiState,
    emergencyContacts: List<EmergencyContactEntity>
) {
    val context = LocalContext.current
    val subTabs = listOf("대체 실내코스", "실시간 교통·날씨", "비상 연락망", "긴급 일본어")

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Top Bar with Rainy Mode Switch
            ContingencyHeaderBanner(
                isRainyMode = uiState.isRainyAlternativeMode,
                onToggleRainyMode = { viewModel.toggleRainyAlternativeMode() }
            )

            // Sub-navigation tabs
            ScrollableTabRow(
                selectedTabIndex = uiState.selectedContingencySubTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = IndigoPrimary,
                modifier = Modifier.fillMaxWidth()
            ) {
                subTabs.forEachIndexed { index, title ->
                    val isSelected = uiState.selectedContingencySubTab == index
                    Tab(
                        selected = isSelected,
                        onClick = { viewModel.selectContingencySubTab(index) },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        },
                        modifier = Modifier.testTag("contingency_subtab_$index")
                    )
                }
            }

            // Sub-screen content
            when (uiState.selectedContingencySubTab) {
                0 -> IndoorAlternativesTab(
                    activities = ContingencyData.alternativeActivities,
                    onOpenMap = { openExternalMap(context, it) }
                )
                1 -> RealtimeTransitWeatherTab(
                    trafficLinks = ContingencyData.trafficLinks,
                    weatherGuides = ContingencyData.weatherGuides,
                    onOpenUrl = { openBrowser(context, it) }
                )
                2 -> EmergencyContactsTab(
                    contacts = emergencyContacts,
                    onCall = { dialPhoneNumber(context, it) },
                    onCopy = { copyToClipboard(context, "연락처", it) },
                    onOpenMap = { openExternalMap(context, it) },
                    onEditContact = { viewModel.openEditContactDialog(it) }
                )
                3 -> EmergencyPhrasesTab(
                    phrases = ContingencyData.emergencyPhrases,
                    onSpeak = { viewModel.speakJapanese(it) },
                    onShowFlashCard = { viewModel.showPhraseFlashCard(it) },
                    onCopy = { copyToClipboard(context, "일본어 회화", it) }
                )
            }
        }

        // FAB to add custom emergency contact when on Emergency Contacts Tab
        if (uiState.selectedContingencySubTab == 2) {
            FloatingActionButton(
                onClick = { viewModel.openAddContactDialog() },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp)
                    .testTag("add_emergency_contact_fab"),
                containerColor = CrimsonAccent,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "비상 연락처 추가")
            }
        }
    }
}

@Composable
fun ContingencyHeaderBanner(
    isRainyMode: Boolean,
    onToggleRainyMode: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isRainyMode) Color(0xFFEFF6FF) else Color(0xFFFEF2F2)
        ),
        border = BorderStroke(1.dp, if (isRainyMode) Color(0xFF93C5FD) else Color(0xFFFECACA)),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    if (isRainyMode) Icons.Default.DirectionsTransit else Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = if (isRainyMode) IndigoPrimary else CrimsonAccent,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = if (isRainyMode) "🌧️ 우천·돌발 안심 플랜 가동 중" else "돌발상황 대비 & SOS 안심 가이드",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isRainyMode) IndigoPrimary else CrimsonAccent
                        )
                    )
                    Text(
                        text = if (isRainyMode) "비/지연 시 안전한 실내 대체 일정 우선 안내" else "기상 악화, 열차 지연, 분실·아이 응급 대비",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "우천 모드",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isRainyMode) IndigoPrimary else Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Switch(
                        checked = isRainyMode,
                        onCheckedChange = { onToggleRainyMode() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = IndigoPrimary,
                            checkedTrackColor = Color(0xFFBFDBFE)
                        ),
                        modifier = Modifier.testTag("rainy_mode_switch")
                    )
                }
            }
        }
    }
}

// ==========================================
// 1. 대체 실내 코스 탭
// ==========================================
@Composable
fun IndoorAlternativesTab(
    activities: List<AlternativeActivity>,
    onOpenMap: (String) -> Unit
) {
    var selectedDayFilter by remember { mutableStateOf(0) } // 0: 전체, 1~6
    val daysList = listOf("전체 Day", "Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6")

    val filteredList = if (selectedDayFilter == 0) {
        activities
    } else {
        activities.filter { it.dayNumber == selectedDayFilter }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("indoor_alternatives_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            // Day filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                daysList.forEachIndexed { index, label ->
                    val isSelected = selectedDayFilter == index
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { selectedDayFilter = index }
                    ) {
                        Text(
                            text = label,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        item {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF1F5F9),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "야외 관광지 비바람·지연 시 부모님과 함께 가기 좋은 쾌적한 실내 명소",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF334155)
                        )
                    )
                }
            }
        }

        items(filteredList, key = { it.id }) { item ->
            AlternativeActivityCard(item = item, onOpenMap = { onOpenMap(item.alternativeLocation) })
        }
    }
}

@Composable
fun AlternativeActivityCard(
    item: AlternativeActivity,
    onOpenMap: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("alternative_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Day & Situation tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = IndigoPrimary
                    ) {
                        Text(
                            text = "Day ${item.dayNumber}",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFFEF3C7)
                    ) {
                        Text(
                            text = item.situation,
                            color = Color(0xFF92400E),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                TextButton(
                    onClick = onOpenMap,
                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp)
                ) {
                    Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(3.dp))
                    Text("지도", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Original Spot
            Text(
                text = "기존 일정: ${item.originalSpot}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.Medium
                )
            )

            // Recommended Alternative Title
            Text(
                text = "➔ 추천 대체: ${item.alternativeTitle}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = IndigoPrimary
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Location
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = null,
                    tint = CrimsonAccent,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = item.alternativeLocation,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Indoor Advantage Box
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = AmberContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = null,
                        tint = AmberWarm,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "부모님 안심 & 실내 쾌적 포인트",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AmberWarm
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.indoorAdvantage,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF78350F),
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Transit Guide
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF1F5F9),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Default.DirectionsTransit,
                        contentDescription = null,
                        tint = CatTransit,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "비 안 맞고 가는 이동 경로",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = CatTransit
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.transitGuide,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }

            // Nearby Safe Food
            if (item.nearbyFoodTip.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = EmeraldContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = EmeraldSafe,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "인근 안심 식당 (회·내장 0%)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldSafe
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.nearbyFoodTip,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF065F46),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 2. 실시간 교통 & 날씨 연동 탭
// ==========================================
@Composable
fun RealtimeTransitWeatherTab(
    trafficLinks: List<RealtimeTrafficLink>,
    weatherGuides: List<CityWeatherGuide>,
    onOpenUrl: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("realtime_tab_column"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "🚆 실시간 철도·교통 운행 현황",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = IndigoPrimary
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "터치 시 공식 철도사(JR 서일본, JR 큐슈 등)의 실시간 열차 지연·운행 상황을 즉시 확인합니다.",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
            )
        }

        items(trafficLinks) { link ->
            TrafficLinkCard(link = link, onOpenUrl = { onOpenUrl(link.url) })
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "⛅ 지역별 실시간 기상 예보 & 주의사항",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = IndigoPrimary
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "일본 기상청(JMA) 및 Yahoo Japan의 9월 말 오사카, 교토, 키타큐슈 실시간 예보 연동입니다.",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
            )
        }

        items(weatherGuides) { guide ->
            WeatherGuideCard(guide = guide, onOpenUrl = { onOpenUrl(guide.officialUrl) })
        }
    }
}

@Composable
fun TrafficLinkCard(
    link: RealtimeTrafficLink,
    onOpenUrl: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = link.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "적용 노선: ${link.lineName}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = CatTransit,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                Button(
                    onClick = onOpenUrl,
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                ) {
                    Icon(Icons.Default.OpenInBrowser, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("실시간 확인", fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = link.description,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFFEF3C7)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = Color(0xFFB45309),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = link.delayTip,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF78350F),
                            fontSize = 11.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherGuideCard(
    guide: CityWeatherGuide,
    onOpenUrl: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        border = BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = AmberWarm,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = guide.cityName,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${guide.region} • 평균 ${guide.avgTemp}",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.outline)
                        )
                    }
                }

                Button(
                    onClick = onOpenUrl,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569))
                ) {
                    Icon(Icons.Default.Thermostat, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("예보 보기", fontSize = 11.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = guide.weatherTips,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )

            Spacer(modifier = Modifier.height(6.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFEFF6FF),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "☔ 우천 시 추천: ${guide.rainyAlternativeSummary}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF1E40AF),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

// ==========================================
// 3. 비상 연락망 탭
// ==========================================
@Composable
fun EmergencyContactsTab(
    contacts: List<EmergencyContactEntity>,
    onCall: (String) -> Unit,
    onCopy: (String) -> Unit,
    onOpenMap: (String) -> Unit,
    onEditContact: (EmergencyContactEntity) -> Unit
) {
    var selectedCategoryFilter by remember { mutableStateOf("전체") }
    val categories = listOf("전체", "대사관/영사관", "숙소", "긴급기관/경찰", "여행자보험/개인")

    val filteredContacts = if (selectedCategoryFilter == "전체") {
        contacts
    } else {
        contacts.filter { it.category == selectedCategoryFilter }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("emergency_contacts_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Category filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategoryFilter == cat
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) CrimsonAccent else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { selectedCategoryFilter = cat }
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFFEF2F2),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = CrimsonAccent, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "'전화 걸기'를 누르면 다이얼 화면으로 번호가 자동 입력됩니다. 필요 시 '+' 버튼으로 나만의 보험/가족 연락처를 추가하세요.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF991B1B), fontSize = 11.sp)
                    )
                }
            }
        }

        items(filteredContacts, key = { it.id }) { contact ->
            EmergencyContactCard(
                contact = contact,
                onCall = { onCall(contact.phoneNumber) },
                onCopy = { onCopy(contact.phoneNumber) },
                onOpenMap = { onOpenMap(contact.address) },
                onEdit = { onEditContact(contact) }
            )
        }
    }
}

@Composable
fun EmergencyContactCard(
    contact: EmergencyContactEntity,
    onCall: () -> Unit,
    onCopy: () -> Unit,
    onOpenMap: () -> Unit,
    onEdit: () -> Unit
) {
    val categoryBadgeColor = when (contact.category) {
        "대사관/영사관" -> Pair(IndigoPrimary, Color(0xFFEFF6FF))
        "숙소" -> Pair(AmberWarm, Color(0xFFFFFBEB))
        "긴급기관/경찰" -> Pair(CrimsonAccent, Color(0xFFFEF2F2))
        else -> Pair(EmeraldSafe, Color(0xFFECFDF5))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("contact_card_${contact.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = categoryBadgeColor.second
                    ) {
                        Text(
                            text = contact.category,
                            color = categoryBadgeColor.first,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    if (contact.isCustom) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFFE2E8F0)
                        ) {
                            Text(
                                text = "사용자 등록",
                                color = Color(0xFF475569),
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                if (contact.isCustom) {
                    IconButton(onClick = onEdit, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "수정", tint = MaterialTheme.colorScheme.outline)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = contact.name,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            // Phone number with dial & copy buttons
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = null,
                        tint = CrimsonAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = contact.phoneNumber,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = CrimsonAccent
                        )
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onCopy, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "복사", modifier = Modifier.size(16.dp))
                    }
                    Button(
                        onClick = onCall,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonAccent)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("통화", fontSize = 12.sp)
                    }
                }
            }

            // Emergency / Sub Phone
            if (contact.emergencyPhone.isNotBlank()) {
                Text(
                    text = "야간/긴급: ${contact.emergencyPhone}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFB45309),
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Address
            if (contact.address.isNotBlank()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = contact.address,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = onOpenMap,
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                    ) {
                        Text("지도", fontSize = 11.sp)
                    }
                }
            }

            // Note
            if (contact.note.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = contact.note,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF334155),
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
        }
    }
}

// ==========================================
// 4. 비상 일본어 회화 탭
// ==========================================
@Composable
fun EmergencyPhrasesTab(
    phrases: List<EmergencyJapanesePhrase>,
    onSpeak: (String) -> Unit,
    onShowFlashCard: (EmergencyJapanesePhrase) -> Unit,
    onCopy: (String) -> Unit
) {
    var selectedCatFilter by remember { mutableStateOf("전체") }
    val categories = listOf("전체", "교통 지연·길찾기", "부모님 질환·약국·응급", "분실·경찰·도움", "식당 주문 (내장 제외·회/와규)")

    val filteredPhrases = if (selectedCatFilter == "전체") {
        phrases
    } else {
        phrases.filter { it.category == selectedCatFilter }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("emergency_phrases_list"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            // Category filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCatFilter == cat
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { selectedCatFilter = cat }
                    ) {
                        Text(
                            text = cat,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }

        item {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFEFF6FF),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Translate, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "'크게 보기'로 현지인에게 바로 보여주거나 '음성 듣기'로 네이티브 발음을 들려주세요.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1E40AF), fontSize = 11.sp)
                    )
                }
            }
        }

        items(filteredPhrases, key = { it.id }) { phrase ->
            EmergencyPhraseCard(
                phrase = phrase,
                onSpeak = { onSpeak(phrase.japaneseText) },
                onShowFlashCard = { onShowFlashCard(phrase) },
                onCopy = { onCopy(phrase.japaneseText) }
            )
        }
    }
}

@Composable
fun EmergencyPhraseCard(
    phrase: EmergencyJapanesePhrase,
    onSpeak: () -> Unit,
    onShowFlashCard: () -> Unit,
    onCopy: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("phrase_card_${phrase.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Category & Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFF1F5F9)
                ) {
                    Text(
                        text = phrase.category,
                        color = Color(0xFF475569),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onCopy, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "복사", modifier = Modifier.size(15.dp))
                    }
                    IconButton(onClick = onSpeak, modifier = Modifier.size(30.dp)) {
                        Icon(Icons.Default.VolumeUp, contentDescription = "음성 듣기", tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                    }
                    Button(
                        onClick = onShowFlashCard,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = IndigoPrimary)
                    ) {
                        Icon(Icons.Default.Fullscreen, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("크게 보기", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Korean Title
            Text(
                text = phrase.koreanTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            // Japanese Text
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = phrase.japaneseText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                )
            }

            // Pronunciation
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "발음: ${phrase.pronunciation}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFB45309),
                    fontWeight = FontWeight.Medium
                )
            )

            // Situation tip
            if (phrase.situationTip.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "💡 ${phrase.situationTip}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

// ==========================================
// Helper Intents
// ==========================================
private fun dialPhoneNumber(context: Context, number: String) {
    try {
        val cleanNumber = number.replace(" ", "").replace("-", "")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber"))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "통화 앱을 실행할 수 없습니다: $number", Toast.LENGTH_SHORT).show()
    }
}

private fun openBrowser(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "웹 브라우저를 열 수 없습니다.", Toast.LENGTH_SHORT).show()
    }
}

private fun openExternalMap(context: Context, query: String) {
    try {
        val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(query))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
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

private fun copyToClipboard(context: Context, label: String, text: String) {
    try {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "$label 복사 완료: $text", Toast.LENGTH_SHORT).show()
    } catch (e: Exception) {
        Toast.makeText(context, "클립보드 복사 실패", Toast.LENGTH_SHORT).show()
    }
}
