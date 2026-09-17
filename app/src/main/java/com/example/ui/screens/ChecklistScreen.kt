package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChecklistEntity
import com.example.ui.TripUiState
import com.example.ui.TripViewModel
import com.example.ui.theme.CrimsonAccent
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.IndigoPrimary

@Composable
fun ChecklistScreen(
    viewModel: TripViewModel,
    uiState: TripUiState,
    checklists: List<ChecklistEntity>
) {
    val totalCount = checklists.size
    val checkedCount = checklists.count { it.isChecked }
    val groupedChecklists = checklists.groupBy { it.category }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("checklist_screen"),
        contentPadding = PaddingValues(16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Progress Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = IndigoPrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "여행 준비물 체크리스트",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        IconButton(
                            onClick = { viewModel.openAddChecklistDialog() },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .testTag("add_checklist_button")
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "추가", tint = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "완료: $checkedCount / $totalCount 항목 준비 완료",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFE2E8F0))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { if (totalCount > 0) checkedCount.toFloat() / totalCount else 0f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF38BDF8),
                        trackColor = Color.White.copy(alpha = 0.2f)
                    )
                }
            }
        }

        // Grouped Checklist items
        groupedChecklists.forEach { (category, items) ->
            item {
                Text(
                    text = category,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(top = 6.dp, start = 4.dp)
                )
            }

            items(items, key = { it.id }) { item ->
                ChecklistItemRow(
                    item = item,
                    onToggle = { viewModel.toggleChecklist(item) },
                    onDelete = { viewModel.deleteChecklist(item) }
                )
            }
        }

        // Emergency Contacts Section
        item {
            EmergencyCard()
        }

        // Reset to Default Itinerary Button
        item {
            OutlinedButton(
                onClick = { viewModel.showResetDialog(true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .testTag("reset_data_button"),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimsonAccent),
                border = BorderStroke(1.dp, CrimsonAccent)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("기본 추천 일정 & 체크리스트 초기화")
            }
        }
    }

    if (uiState.showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.showResetDialog(false) },
            title = { Text("일정 초기화 확인") },
            text = { Text("모든 일정을 초기 추천 일정(9월 20일-25일 오사카 및 키타큐슈 완벽 코스)으로 되돌리시겠습니까? 직접 추가하신 일정은 삭제됩니다.") },
            confirmButton = {
                Button(
                    onClick = { viewModel.resetAllData() },
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonAccent)
                ) {
                    Text("초기화")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.showResetDialog(false) }) {
                    Text("취소")
                }
            }
        )
    }
}

@Composable
fun ChecklistItemRow(
    item: ChecklistEntity,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .testTag("checklist_item_${item.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (item.isChecked) Color(0xFFF8FAFC) else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp,
            if (item.isChecked) Color(0xFFE2E8F0) else MaterialTheme.colorScheme.outlineVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    if (item.isChecked) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                    contentDescription = null,
                    tint = if (item.isChecked) EmeraldSafe else MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = if (item.isChecked) FontWeight.Normal else FontWeight.Medium,
                        textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                        color = if (item.isChecked) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "삭제",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun EmergencyCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
        border = BorderStroke(1.dp, Color(0xFFFDE68A))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Call, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "긴급 상황 & 현지 비상 연락처",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F)
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            val contacts = listOf(
                "경찰 긴급전화: 국번없이 110",
                "구급차 / 화재: 국번없이 119",
                "주오사카 대한민국 총영사관: +81-6-4256-2345 (야간 긴급 +81-90-5050-2240)",
                "주후쿠오카 대한민국 총영사관: +81-92-771-0461 (야간 긴급 +81-90-1367-3638)",
                "외교부 영사콜센터: +82-2-3210-0404 (24시간 카카오톡 상담 가능)"
            )
            contacts.forEach { text ->
                Text(
                    text = "• $text",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF92400E),
                        lineHeight = 18.sp
                    ),
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}
