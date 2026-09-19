package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.TripScheduleEntity
import com.example.ui.theme.CrimsonAccent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditScheduleDialog(
    initialSchedule: TripScheduleEntity?,
    defaultDay: Int,
    onDismiss: () -> Unit,
    onSave: (TripScheduleEntity) -> Unit,
    onDelete: ((TripScheduleEntity) -> Unit)? = null
) {
    var dayNumber by remember { mutableIntStateOf(initialSchedule?.dayNumber ?: defaultDay) }
    var timeSlot by remember { mutableStateOf(initialSchedule?.timeSlot ?: "10:00 - 11:30") }
    var title by remember { mutableStateOf(initialSchedule?.title ?: "") }
    var category by remember { mutableStateOf(initialSchedule?.category ?: "관광") }
    var locationName by remember { mutableStateOf(initialSchedule?.locationName ?: "") }
    var transitGuide by remember { mutableStateOf(initialSchedule?.transitGuide ?: "") }
    var description by remember { mutableStateOf(initialSchedule?.description ?: "") }
    var kidsFriendlyTip by remember { mutableStateOf(initialSchedule?.kidsFriendlyTip ?: "") }
    var restaurantName by remember { mutableStateOf(initialSchedule?.restaurantName ?: "") }
    var restaurantMenu by remember { mutableStateOf(initialSchedule?.restaurantMenu ?: "") }
    var customMemo by remember { mutableStateOf(initialSchedule?.customMemo ?: "") }

    val categories = listOf("관광", "맛집", "교통", "쇼핑", "온천", "숙소")

    val dayDates = mapOf(
        1 to Pair("9월 20일 (일)", "오사카"),
        2 to Pair("9월 21일 (월)", "교토"),
        3 to Pair("9월 22일 (화)", "오사카"),
        4 to Pair("9월 23일 (수)", "키타큐슈"),
        5 to Pair("9월 24일 (목)", "키타큐슈"),
        6 to Pair("9월 25일 (금)", "키타큐슈")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialSchedule == null) "새 일정 추가" else "일정 수정",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Day selector
                Text("여행 일차 선택", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    (1..6).forEach { day ->
                        val isSelected = dayNumber == day
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { dayNumber = day }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "D$day",
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }

                // Category selector
                Text("카테고리", style = MaterialTheme.typography.labelMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = category == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { category = cat }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = timeSlot,
                    onValueChange = { timeSlot = it },
                    label = { Text("시간대 (예: 10:00 - 11:30)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dialog_timeslot_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("일정 제목 *") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("dialog_title_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = locationName,
                    onValueChange = { locationName = it },
                    label = { Text("장소 / 위치 명칭") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = transitGuide,
                    onValueChange = { transitGuide = it },
                    label = { Text("이동 동선 및 교통수단 (지하철, 신칸센, 버스)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("상세 일정 설명") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                OutlinedTextField(
                    value = kidsFriendlyTip,
                    onValueChange = { kidsFriendlyTip = it },
                    label = { Text("부모님 안심 꿀팁 / 주의사항") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                OutlinedTextField(
                    value = restaurantName,
                    onValueChange = { restaurantName = it },
                    label = { Text("현지인 맛집 (노회, 노내장)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = restaurantMenu,
                    onValueChange = { restaurantMenu = it },
                    label = { Text("추천 메뉴 (부모님 추천 안심 메뉴)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = customMemo,
                    onValueChange = { customMemo = it },
                    label = { Text("개인 메모") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                if (initialSchedule != null && onDelete != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { onDelete(initialSchedule) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dialog_delete_schedule_button"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = CrimsonAccent
                        )
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "삭제")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("이 일정 삭제하기")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val dateInfo = dayDates[dayNumber] ?: Pair("9월 20일", "오사카")
                        val updated = (initialSchedule ?: TripScheduleEntity(
                            dayNumber = dayNumber,
                            dateText = dateInfo.first,
                            city = dateInfo.second,
                            timeSlot = timeSlot,
                            title = title,
                            category = category,
                            locationName = locationName,
                            transitGuide = transitGuide,
                            description = description,
                            kidsFriendlyTip = kidsFriendlyTip,
                            restaurantName = restaurantName,
                            restaurantMenu = restaurantMenu,
                            customMemo = customMemo
                        )).copy(
                            dayNumber = dayNumber,
                            dateText = dateInfo.first,
                            city = dateInfo.second,
                            timeSlot = timeSlot,
                            title = title,
                            category = category,
                            locationName = locationName,
                            transitGuide = transitGuide,
                            description = description,
                            kidsFriendlyTip = kidsFriendlyTip,
                            restaurantName = restaurantName,
                            restaurantMenu = restaurantMenu,
                            customMemo = customMemo
                        )
                        onSave(updated)
                    }
                },
                modifier = Modifier.testTag("dialog_save_schedule_button"),
                enabled = title.isNotBlank()
            ) {
                Text("저장")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("dialog_cancel_schedule_button")
            ) {
                Text("취소")
            }
        }
    )
}
