package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.EmergencyContactEntity
import com.example.ui.theme.CrimsonAccent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEmergencyContactDialog(
    initialContact: EmergencyContactEntity? = null,
    onDismiss: () -> Unit,
    onSave: (EmergencyContactEntity) -> Unit,
    onDelete: ((EmergencyContactEntity) -> Unit)? = null
) {
    var name by remember { mutableStateOf(initialContact?.name ?: "") }
    var category by remember { mutableStateOf(initialContact?.category ?: "여행자보험/개인") }
    var phoneNumber by remember { mutableStateOf(initialContact?.phoneNumber ?: "") }
    var emergencyPhone by remember { mutableStateOf(initialContact?.emergencyPhone ?: "") }
    var address by remember { mutableStateOf(initialContact?.address ?: "") }
    var note by remember { mutableStateOf(initialContact?.note ?: "") }

    val categories = listOf("여행자보험/개인", "숙소", "긴급기관/경찰", "대사관/영사관")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialContact == null) "비상 연락처 직접 추가" else "비상 연락처 수정",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("카테고리", style = MaterialTheme.typography.labelMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
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
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("기관/연락처 명칭 * (예: 현대해상 여행보험)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_name_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = { Text("전화번호 * (예: +82-2-1234-5678)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("contact_phone_input"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = emergencyPhone,
                    onValueChange = { emergencyPhone = it },
                    label = { Text("보조/야간 긴급번호 (선택)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("주소 또는 위치 (선택)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("메모 / 증권번호 / 특이사항 (선택)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phoneNumber.isNotBlank()) {
                        val contact = (initialContact ?: EmergencyContactEntity(
                            name = name.trim(),
                            category = category,
                            phoneNumber = phoneNumber.trim(),
                            isCustom = true
                        )).copy(
                            name = name.trim(),
                            category = category,
                            phoneNumber = phoneNumber.trim(),
                            emergencyPhone = emergencyPhone.trim(),
                            address = address.trim(),
                            note = note.trim()
                        )
                        onSave(contact)
                    }
                },
                enabled = name.isNotBlank() && phoneNumber.isNotBlank(),
                modifier = Modifier.testTag("contact_save_button")
            ) {
                Text("저장")
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (initialContact != null && onDelete != null && initialContact.isCustom) {
                    TextButton(
                        onClick = { onDelete(initialContact) },
                        colors = ButtonDefaults.textButtonColors(contentColor = CrimsonAccent)
                    ) {
                        Text("삭제")
                    }
                }
                TextButton(onClick = onDismiss) {
                    Text("취소")
                }
            }
        }
    )
}
