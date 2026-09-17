package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trip_schedules ORDER BY dayNumber ASC, id ASC")
    fun getAllSchedules(): Flow<List<TripScheduleEntity>>

    @Query("SELECT * FROM trip_schedules WHERE dayNumber = :day ORDER BY id ASC")
    fun getSchedulesByDay(day: Int): Flow<List<TripScheduleEntity>>

    @Query("SELECT COUNT(*) FROM trip_schedules")
    suspend fun getScheduleCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<TripScheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: TripScheduleEntity): Long

    @Update
    suspend fun updateSchedule(schedule: TripScheduleEntity)

    @Delete
    suspend fun deleteSchedule(schedule: TripScheduleEntity)

    @Query("UPDATE trip_schedules SET isCompleted = :completed WHERE id = :id")
    suspend fun setScheduleCompleted(id: Long, completed: Boolean)

    @Query("DELETE FROM trip_schedules")
    suspend fun clearAllSchedules()

    // Checklists
    @Query("SELECT * FROM checklists ORDER BY id ASC")
    fun getAllChecklists(): Flow<List<ChecklistEntity>>

    @Query("SELECT COUNT(*) FROM checklists")
    suspend fun getChecklistCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklists(items: List<ChecklistEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklist(item: ChecklistEntity): Long

    @Update
    suspend fun updateChecklist(item: ChecklistEntity)

    @Delete
    suspend fun deleteChecklist(item: ChecklistEntity)

    @Query("DELETE FROM checklists")
    suspend fun clearAllChecklists()

    // Emergency Contacts
    @Query("SELECT * FROM emergency_contacts ORDER BY isCustom DESC, id ASC")
    fun getAllEmergencyContacts(): Flow<List<EmergencyContactEntity>>

    @Query("SELECT COUNT(*) FROM emergency_contacts")
    suspend fun getEmergencyContactCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContacts(contacts: List<EmergencyContactEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmergencyContact(contact: EmergencyContactEntity): Long

    @Update
    suspend fun updateEmergencyContact(contact: EmergencyContactEntity)

    @Delete
    suspend fun deleteEmergencyContact(contact: EmergencyContactEntity)

    @Query("DELETE FROM emergency_contacts WHERE isCustom = 1")
    suspend fun clearCustomEmergencyContacts()
}
