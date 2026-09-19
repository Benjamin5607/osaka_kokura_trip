package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TripScheduleEntity
import com.example.ui.MainTab
import com.example.ui.TripUiState
import com.example.ui.TripViewModel
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberWarm
import com.example.ui.theme.CatGourmet
import com.example.ui.theme.CatGourmetBg
import com.example.ui.theme.CatHotel
import com.example.ui.theme.CatHotelBg
import com.example.ui.theme.CatOnsen
import com.example.ui.theme.CatOnsenBg
import com.example.ui.theme.CatShopping
import com.example.ui.theme.CatShoppingBg
import com.example.ui.theme.CatSightseeing
import com.example.ui.theme.CatSightseeingBg
import com.example.ui.theme.CatTransit
import com.example.ui.theme.CatTransitBg
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.IndigoPrimary

@Composable
fun ScheduleScreen(
    viewModel: TripViewModel,
    uiState: TripUiState,
    schedules: List<TripScheduleEntity>
) {
    val context = LocalContext.current
    val totalCount = schedules.size
    val completedCount = schedules.count { it.isCompleted }

    val daysInfo = listOf(
        Triple(1, "9/20 (일)", "오사카 캐슬·신세카이"),
        Triple(2, "9/21 (월)", "교토 투어·도톤보리"),
        Triple(3, "9/22 (화)", "오사카 쇼핑·온천"),
        Triple(4, "9/23 (수)", "키타큐슈·고쿠라성"),
        Triple(5, "9/24 (목)", "모지코·간몬 해저터널"),
        Triple(6, "9/25 (금)", "고쿠라 여유·아울렛"),
        Triple(7, "9/26 (토)", "하카타·공항·부산귀국")
    )

    val currentHotel = if (uiState.selectedDay <= 3) {
        "신오사카 써니스톤 호텔 (니시나카지마역 1분, 짐보관)"
    } else {
        "JR큐슈 스테이션 호텔 고쿠라 (고쿠라역사 직결)"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("schedule_list_column"),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Day selector tabs
            item {
                DaySelectorBar(
                    selectedDay = uiState.selectedDay,
                    days = daysInfo,
                    onSelectDay = { viewModel.selectDay(it) }
                )
            }

            // Hotel & Day summary banner
            item {
                HotelInfoBanner(
                    day = uiState.selectedDay,
                    hotelText = currentHotel,
                    dateText = daysInfo.getOrNull(uiState.selectedDay - 1)?.second ?: "",
                    summaryText = daysInfo.getOrNull(uiState.selectedDay - 1)?.third ?: ""
                )
            }

            // Contingency & Rainy Alternative Callout Banner
            item {
                ScheduleContingencyBanner(
                    day = uiState.selectedDay,
                    isRainyMode = uiState.isRainyAlternativeMode,
                    onClickContingency = {
                        viewModel.selectContingencySubTab(0)
                        viewModel.selectTab(MainTab.CONTINGENCY)
                    }
                )
            }

            // Category filter chips
            item {
                CategoryFilterBar(
                    selectedCategory = uiState.selectedCategory,
                    onSelectCategory = { viewModel.selectCategory(it) }
                )
            }

            // Search bar & progress
            item {
                ScheduleSearchAndProgress(
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = { viewModel.setSearchQuery(it) },
                    completedCount = completedCount,
                    totalCount = totalCount
                )
            }

            // Schedule Cards
            if (schedules.isEmpty()) {
                item {
                    EmptyScheduleView(
                        onAddClick = { viewModel.openAddScheduleDialog() }
                    )
                }
            } else {
                items(schedules, key = { it.id }) { schedule ->
                    ScheduleCard(
                        schedule = schedule,
                        onToggleComplete = { viewModel.toggleScheduleCompletion(schedule) },
                        onEdit = { viewModel.openEditScheduleDialog(schedule) },
                        onSaveMemo = { memo -> viewModel.updateScheduleMemo(schedule, memo) },
                        onOpenMap = { openGoogleMaps(context, schedule.locationName.ifBlank { schedule.title }) }
                    )
                }
            }
        }

        // Floating Action Button to add custom schedule
        FloatingActionButton(
            onClick = { viewModel.openAddScheduleDialog() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("add_schedule_fab"),
            containerColor = CrimsonAccent,
            contentColor = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "일정 추가")
        }
    }
}

@Composable
fun DaySelectorBar(
    selectedDay: Int,
    days: List<Triple<Int, String, String>>,
    onSelectDay: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        days.forEach { (day, date, title) ->
            val isSelected = selectedDay == day
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.surface,
                tonalElevation = if (isSelected) 4.dp else 1.dp,
                border = BorderStroke(
                    1.dp,
                    if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSelectDay(day) }
                    .testTag("day_tab_$day")
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Day $day",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (isSelected) Color(0xFFE2E8F0) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun HotelInfoBanner(
    day: Int,
    hotelText: String,
    dateText: String,
    summaryText: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (day <= 3) Color(0xFFEFF6FF) else Color(0xFFFFFBEB)
        ),
        border = BorderStroke(
            1.dp,
            if (day <= 3) Color(0xFFBFDBFE) else Color(0xFFFDE68A)
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(if (day <= 3) IndigoPrimary else AmberWarm)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (day <= 3) "오사카·교토" else "키타큐슈",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$dateText : $summaryText",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Hotel,
                    contentDescription = null,
                    tint = if (day <= 3) IndigoPrimary else AmberWarm,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = hotelText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}

@Composable
fun ScheduleContingencyBanner(
    day: Int,
    isRainyMode: Boolean,
    onClickContingency: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClickContingency() }
            .testTag("schedule_contingency_banner"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isRainyMode) Color(0xFFEFF6FF) else Color(0xFFFFFBEB)
        ),
        border = BorderStroke(1.dp, if (isRainyMode) Color(0xFFBFDBFE) else Color(0xFFFDE68A))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isRainyMode) "🌧️" else "☔",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (isRainyMode) "Day $day 우천 모드 가동 중" else "Day $day 비·지연 돌발상황 대비",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isRainyMode) IndigoPrimary else Color(0xFF92400E)
                        )
                    )
                    Text(
                        text = "실내 대체 코스, 실시간 열차 지연, 안심 SOS 연락처",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF475569),
                            fontSize = 11.sp
                        )
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isRainyMode) IndigoPrimary else AmberWarm
            ) {
                Text(
                    text = "대비책 보기",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryFilterBar(
    selectedCategory: String,
    onSelectCategory: (String) -> Unit
) {
    val categories = listOf("전체", "관광", "맛집", "교통", "쇼핑", "온천", "숙소")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        categories.forEach { cat ->
            val isSelected = selectedCategory == cat
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSelectCategory(cat) }
                    .testTag("filter_chip_$cat")
            ) {
                Text(
                    text = cat,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}

@Composable
fun ScheduleSearchAndProgress(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    completedCount: Int,
    totalCount: Int
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("장소, 이동수단, 맛집, 아이 팁 검색...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "지우기")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("schedule_search_input"),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "오늘의 일정 진행률: $completedCount / $totalCount 완료",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                text = if (totalCount > 0) "${(completedCount * 100) / totalCount}%" else "0%",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { if (totalCount > 0) completedCount.toFloat() / totalCount else 0f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = CrimsonAccent,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun ScheduleCard(
    schedule: TripScheduleEntity,
    onToggleComplete: () -> Unit,
    onEdit: () -> Unit,
    onSaveMemo: (String) -> Unit,
    onOpenMap: () -> Unit
) {
    var isMemoExpanded by remember { mutableStateOf(false) }
    var memoInput by remember(schedule.customMemo) { mutableStateOf(schedule.customMemo) }

    val categoryColor = when (schedule.category) {
        "교통" -> Pair(CatTransit, CatTransitBg)
        "맛집" -> Pair(CatGourmet, CatGourmetBg)
        "관광" -> Pair(CatSightseeing, CatSightseeingBg)
        "쇼핑" -> Pair(CatShopping, CatShoppingBg)
        "온천" -> Pair(CatOnsen, CatOnsenBg)
        else -> Pair(CatHotel, CatHotelBg)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("schedule_card_${schedule.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (schedule.isCompleted) Color(0xFFF8FAFC) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (schedule.isCompleted) 1.dp else 2.dp),
        border = BorderStroke(
            1.dp,
            if (schedule.isCompleted) Color(0xFFE2E8F0) else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top Row: Time, Category, Complete Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = schedule.timeSlot,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    // Category chip
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = categoryColor.second,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    ) {
                        Text(
                            text = schedule.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = categoryColor.first
                            )
                        )
                    }
                }

                // Completion toggle
                IconButton(
                    onClick = onToggleComplete,
                    modifier = Modifier.testTag("toggle_complete_${schedule.id}")
                ) {
                    Icon(
                        if (schedule.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "완료 표시",
                        tint = if (schedule.isCompleted) EmeraldSafe else MaterialTheme.colorScheme.outline
                    )
                }
            }

            // Title
            Text(
                text = schedule.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (schedule.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (schedule.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Location with Map intent button
            if (schedule.locationName.isNotBlank()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        tint = CrimsonAccent,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = schedule.locationName,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = onOpenMap,
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 0.dp),
                        modifier = Modifier.testTag("map_button_${schedule.id}")
                    ) {
                        Icon(Icons.Default.Map, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text("지도 보기", fontSize = 12.sp)
                    }
                }
            }

            // Transit Guide Box
            if (schedule.transitGuide.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF1F5F9),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
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
                                text = "이동 동선 & 교통수단",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CatTransit
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = schedule.transitGuide,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }

            // Description
            if (schedule.description.isNotBlank()) {
                Text(
                    text = schedule.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // Kids Friendly Tip Box
            if (schedule.kidsFriendlyTip.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = AmberContainer,
                    border = BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.ChildCare,
                            contentDescription = null,
                            tint = AmberWarm,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "초등학생 맞춤 꿀팁 & 주의사항",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AmberWarm
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = schedule.kidsFriendlyTip,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF78350F),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }

            // Local Japanese Restaurant Recommendation Box
            if (schedule.restaurantName.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = EmeraldContainer,
                    border = BorderStroke(1.dp, Color(0xFFA7F3D0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Restaurant,
                                contentDescription = null,
                                tint = EmeraldSafe,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "★ 일본인 현지 맛집 (회·내장 0% 안심)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldSafe
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = schedule.restaurantName,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF065F46)
                            )
                        )
                        if (schedule.restaurantMenu.isNotBlank()) {
                            Text(
                                text = "추천메뉴: ${schedule.restaurantMenu}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF047857),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                        if (schedule.restaurantFeature.isNotBlank()) {
                            Text(
                                text = schedule.restaurantFeature,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF064E3B)
                                )
                            )
                        }
                    }
                }
            }

            // Custom Memo Section
            if (schedule.customMemo.isNotBlank() && !isMemoExpanded) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF3F4F6),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { isMemoExpanded = true }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Note, contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "메모: ${schedule.customMemo}",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF374151)),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isMemoExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    OutlinedTextField(
                        value = memoInput,
                        onValueChange = { memoInput = it },
                        label = { Text("나만의 여행 메모 입력") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { isMemoExpanded = false }) {
                            Text("닫기")
                        }
                        Button(
                            onClick = {
                                onSaveMemo(memoInput)
                                isMemoExpanded = false
                            }
                        ) {
                            Text("저장")
                        }
                    }
                }
            }

            // Bottom action row: Edit, Add Memo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isMemoExpanded && schedule.customMemo.isBlank()) {
                    TextButton(
                        onClick = { isMemoExpanded = true },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.Note, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("+ 메모", fontSize = 12.sp)
                    }
                }

                TextButton(
                    onClick = onEdit,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("edit_button_${schedule.id}")
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("수정", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun EmptyScheduleView(onAddClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.Schedule,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.outlineVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "해당 조건의 일정이 없습니다.",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "새로운 일정을 추가해 보세요!",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("새 일정 추가")
            }
        }
    }
}

private fun openGoogleMaps(context: Context, query: String) {
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
