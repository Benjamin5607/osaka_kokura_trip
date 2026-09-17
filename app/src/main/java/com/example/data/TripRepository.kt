package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TripRepository(private val dao: TripDao) {

    val allSchedules: Flow<List<TripScheduleEntity>> = dao.getAllSchedules()

    fun getSchedulesByDay(day: Int): Flow<List<TripScheduleEntity>> = dao.getSchedulesByDay(day)

    val allChecklists: Flow<List<ChecklistEntity>> = dao.getAllChecklists()

    val allEmergencyContacts: Flow<List<EmergencyContactEntity>> = dao.getAllEmergencyContacts()

    suspend fun ensureInitialData() = withContext(Dispatchers.IO) {
        if (dao.getScheduleCount() == 0) {
            dao.insertSchedules(InitialTripData.getDefaultSchedules())
        }
        if (dao.getChecklistCount() == 0) {
            dao.insertChecklists(InitialTripData.getDefaultChecklists())
        }
        if (dao.getEmergencyContactCount() == 0) {
            dao.insertEmergencyContacts(InitialTripData.getDefaultEmergencyContacts())
        }
    }

    suspend fun insertSchedule(schedule: TripScheduleEntity) = withContext(Dispatchers.IO) {
        dao.insertSchedule(schedule)
    }

    suspend fun updateSchedule(schedule: TripScheduleEntity) = withContext(Dispatchers.IO) {
        dao.updateSchedule(schedule)
    }

    suspend fun deleteSchedule(schedule: TripScheduleEntity) = withContext(Dispatchers.IO) {
        dao.deleteSchedule(schedule)
    }

    suspend fun toggleScheduleCompletion(id: Long, completed: Boolean) = withContext(Dispatchers.IO) {
        dao.setScheduleCompleted(id, completed)
    }

    suspend fun insertChecklist(item: ChecklistEntity) = withContext(Dispatchers.IO) {
        dao.insertChecklist(item)
    }

    suspend fun updateChecklist(item: ChecklistEntity) = withContext(Dispatchers.IO) {
        dao.updateChecklist(item)
    }

    suspend fun deleteChecklist(item: ChecklistEntity) = withContext(Dispatchers.IO) {
        dao.deleteChecklist(item)
    }

    suspend fun insertEmergencyContact(contact: EmergencyContactEntity) = withContext(Dispatchers.IO) {
        dao.insertEmergencyContact(contact)
    }

    suspend fun updateEmergencyContact(contact: EmergencyContactEntity) = withContext(Dispatchers.IO) {
        dao.updateEmergencyContact(contact)
    }

    suspend fun deleteEmergencyContact(contact: EmergencyContactEntity) = withContext(Dispatchers.IO) {
        dao.deleteEmergencyContact(contact)
    }

    suspend fun resetToDefault() = withContext(Dispatchers.IO) {
        dao.clearAllSchedules()
        dao.clearAllChecklists()
        dao.clearCustomEmergencyContacts()
        dao.insertSchedules(InitialTripData.getDefaultSchedules())
        dao.insertChecklists(InitialTripData.getDefaultChecklists())
        if (dao.getEmergencyContactCount() == 0) {
            dao.insertEmergencyContacts(InitialTripData.getDefaultEmergencyContacts())
        }
    }
}
