package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChecklistEntity
import com.example.data.ContingencyData
import com.example.data.EmergencyContactEntity
import com.example.data.EmergencyJapanesePhrase
import com.example.data.TripDatabase
import com.example.data.TripRepository
import com.example.data.TripScheduleEntity
import android.speech.tts.TextToSpeech
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab(val title: String) {
    SCHEDULE("일정표"),
    TRANSIT_PASS("교통&패스"),
    GOURMET("현지맛집"),
    CONTINGENCY("돌발·SOS"),
    CHECKLIST("체크리스트")
}

data class TripUiState(
    val selectedDay: Int = 1,
    val selectedCategory: String = "전체",
    val selectedTab: MainTab = MainTab.SCHEDULE,
    val searchQuery: String = "",
    val isAddScheduleDialogOpen: Boolean = false,
    val editingSchedule: TripScheduleEntity? = null,
    val isAddChecklistDialogOpen: Boolean = false,
    val showResetConfirmDialog: Boolean = false,
    // 돌발상황 및 비상대응 관련 상태
    val isRainyAlternativeMode: Boolean = false,
    val selectedContingencySubTab: Int = 0, // 0: 대체 실내코스, 1: 실시간 정보, 2: 비상연락망, 3: 비상 일본어
    val isAddContactDialogOpen: Boolean = false,
    val editingContact: EmergencyContactEntity? = null,
    val selectedPhraseForFlashCard: EmergencyJapanesePhrase? = null
)

class TripViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TripRepository

    private val _uiState = MutableStateFlow(TripUiState())
    val uiState: StateFlow<TripUiState> = _uiState.asStateFlow()

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    init {
        val db = TripDatabase.getDatabase(application, viewModelScope)
        repository = TripRepository(db.tripDao())
        viewModelScope.launch {
            repository.ensureInitialData()
        }
        try {
            tts = TextToSpeech(application) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val result = tts?.setLanguage(Locale.JAPANESE)
                    isTtsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
                }
            }
        } catch (e: Exception) {
            isTtsReady = false
        }
    }

    val allSchedules: StateFlow<List<TripScheduleEntity>> = repository.allSchedules
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allChecklists: StateFlow<List<ChecklistEntity>> = repository.allChecklists
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allEmergencyContacts: StateFlow<List<EmergencyContactEntity>> = repository.allEmergencyContacts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Filtered schedules based on day and category
    val filteredSchedules: StateFlow<List<TripScheduleEntity>> = combine(
        allSchedules,
        _uiState
    ) { schedules, state ->
        schedules.filter { schedule ->
            val matchDay = schedule.dayNumber == state.selectedDay
            val matchCategory = if (state.selectedCategory == "전체") true else schedule.category == state.selectedCategory
            val matchQuery = if (state.searchQuery.isBlank()) true else {
                schedule.title.contains(state.searchQuery, ignoreCase = true) ||
                schedule.locationName.contains(state.searchQuery, ignoreCase = true) ||
                schedule.transitGuide.contains(state.searchQuery, ignoreCase = true) ||
                schedule.restaurantName.contains(state.searchQuery, ignoreCase = true) ||
                schedule.kidsFriendlyTip.contains(state.searchQuery, ignoreCase = true)
            }
            matchDay && matchCategory && matchQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectDay(day: Int) {
        _uiState.value = _uiState.value.copy(selectedDay = day)
    }

    fun selectCategory(category: String) {
        _uiState.value = _uiState.value.copy(selectedCategory = category)
    }

    fun selectTab(tab: MainTab) {
        _uiState.value = _uiState.value.copy(selectedTab = tab)
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun toggleScheduleCompletion(schedule: TripScheduleEntity) {
        viewModelScope.launch {
            repository.toggleScheduleCompletion(schedule.id, !schedule.isCompleted)
        }
    }

    fun saveSchedule(schedule: TripScheduleEntity) {
        viewModelScope.launch {
            if (schedule.id == 0L) {
                repository.insertSchedule(schedule)
            } else {
                repository.updateSchedule(schedule)
            }
            closeScheduleDialog()
        }
    }

    fun deleteSchedule(schedule: TripScheduleEntity) {
        viewModelScope.launch {
            repository.deleteSchedule(schedule)
            closeScheduleDialog()
        }
    }

    fun updateScheduleMemo(schedule: TripScheduleEntity, memo: String) {
        viewModelScope.launch {
            repository.updateSchedule(schedule.copy(customMemo = memo))
        }
    }

    fun openAddScheduleDialog() {
        _uiState.value = _uiState.value.copy(
            isAddScheduleDialogOpen = true,
            editingSchedule = null
        )
    }

    fun openEditScheduleDialog(schedule: TripScheduleEntity) {
        _uiState.value = _uiState.value.copy(
            isAddScheduleDialogOpen = true,
            editingSchedule = schedule
        )
    }

    fun closeScheduleDialog() {
        _uiState.value = _uiState.value.copy(
            isAddScheduleDialogOpen = false,
            editingSchedule = null
        )
    }

    // Checklist operations
    fun toggleChecklist(item: ChecklistEntity) {
        viewModelScope.launch {
            repository.updateChecklist(item.copy(isChecked = !item.isChecked))
        }
    }

    fun addChecklist(title: String, category: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.insertChecklist(ChecklistEntity(title = title.trim(), category = category))
            closeAddChecklistDialog()
        }
    }

    fun deleteChecklist(item: ChecklistEntity) {
        viewModelScope.launch {
            repository.deleteChecklist(item)
        }
    }

    fun openAddChecklistDialog() {
        _uiState.value = _uiState.value.copy(isAddChecklistDialogOpen = true)
    }

    fun closeAddChecklistDialog() {
        _uiState.value = _uiState.value.copy(isAddChecklistDialogOpen = false)
    }

    fun showResetDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showResetConfirmDialog = show)
    }

    fun resetAllData() {
        viewModelScope.launch {
            repository.resetToDefault()
            showResetDialog(false)
        }
    }

    // 돌발상황 / 비상대응 (Contingency & SOS) Operations
    fun toggleRainyAlternativeMode() {
        _uiState.value = _uiState.value.copy(
            isRainyAlternativeMode = !_uiState.value.isRainyAlternativeMode
        )
    }

    fun selectContingencySubTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedContingencySubTab = index)
    }

    fun speakJapanese(text: String) {
        if (isTtsReady) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "phrase_tts")
        }
    }

    fun openAddContactDialog() {
        _uiState.value = _uiState.value.copy(
            isAddContactDialogOpen = true,
            editingContact = null
        )
    }

    fun openEditContactDialog(contact: EmergencyContactEntity) {
        _uiState.value = _uiState.value.copy(
            isAddContactDialogOpen = true,
            editingContact = contact
        )
    }

    fun closeContactDialog() {
        _uiState.value = _uiState.value.copy(
            isAddContactDialogOpen = false,
            editingContact = null
        )
    }

    fun saveEmergencyContact(contact: EmergencyContactEntity) {
        viewModelScope.launch {
            if (contact.id == 0L) {
                repository.insertEmergencyContact(contact.copy(isCustom = true))
            } else {
                repository.updateEmergencyContact(contact)
            }
            closeContactDialog()
        }
    }

    fun deleteEmergencyContact(contact: EmergencyContactEntity) {
        viewModelScope.launch {
            repository.deleteEmergencyContact(contact)
            closeContactDialog()
        }
    }

    fun showPhraseFlashCard(phrase: EmergencyJapanesePhrase?) {
        _uiState.value = _uiState.value.copy(selectedPhraseForFlashCard = phrase)
    }

    override fun onCleared() {
        super.onCleared()
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            // Ignore on cleanup
        }
    }
}
