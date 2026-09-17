package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip_schedules")
data class TripScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val dayNumber: Int,              // 1 to 6
    val dateText: String,            // e.g., "9월 20일 (토)"
    val city: String,                // "오사카", "교토", "키타큐슈"
    val timeSlot: String,            // "10:00 - 11:30"
    val title: String,
    val category: String,            // "교통", "관광", "맛집", "쇼핑", "온천", "숙소"
    val locationName: String,
    val transitGuide: String,        // subway, bus, walk, pass usage
    val description: String,
    val kidsFriendlyTip: String,     // Tips for elementary school kids
    val restaurantName: String = "",
    val restaurantMenu: String = "", // strictly no sashimi / no offal
    val restaurantFeature: String = "",
    val isCompleted: Boolean = false,
    val customMemo: String = ""
)

@Entity(tableName = "checklists")
data class ChecklistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,            // "필수 서류/패스", "아이 필수품", "전자기기/편의", "의약품/상비"
    val isChecked: Boolean = false
)

@Entity(tableName = "emergency_contacts")
data class EmergencyContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val category: String,            // "대사관/영사관", "숙소", "긴급기관/경찰", "여행자보험/개인"
    val phoneNumber: String,
    val emergencyPhone: String = "", // 야간/휴일 비상전화
    val address: String = "",
    val note: String = "",           // 영사 조력 내용, 한국어 지원 여부 등
    val isCustom: Boolean = false    // 사용자가 직접 추가한 연락처 여부
)

