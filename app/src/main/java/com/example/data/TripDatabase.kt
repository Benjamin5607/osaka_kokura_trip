package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [TripScheduleEntity::class, ChecklistEntity::class, EmergencyContactEntity::class],
    version = 2,
    exportSchema = false
)
abstract class TripDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TripDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDatabase::class.java,
                    "osaka_kitakyushu_trip.db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            scope.launch(Dispatchers.IO) {
                                populateInitialData(database.tripDao())
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateInitialData(dao: TripDao) {
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
    }
}
