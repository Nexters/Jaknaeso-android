package com.jaknaeso.app.data.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Round::class], version = 1, exportSchema = false)
abstract class RoundDatabase : RoomDatabase() {
    abstract fun roundDao(): RoundDao

    class DatabaseCallback @Inject constructor(
        private val database: Provider<RoundDatabase>
    ) : RoomDatabase.Callback() {
        private val _isDatabaseInitialized = MutableStateFlow(false)
        val isDatabaseInitialized = _isDatabaseInitialized.asStateFlow()

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val dao = database.get().roundDao()
                populateInitialData(dao)
                _isDatabaseInitialized.value = true
            }
        }

        private suspend fun populateInitialData(dao: RoundDao) {
            val initialRounds = listOf(
                Round(0, isLocked = false, isCompleted = false, isTodayQuestion = false),
                Round(1, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(2, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(3, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(4, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(5, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(6, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(7, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(8, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(9, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(10, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(11, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(12, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(13, isLocked = true, isCompleted = false, isTodayQuestion = false),
                Round(14, isLocked = true, isCompleted = false, isTodayQuestion = false),
            )
            dao.insertRounds(initialRounds)
        }
    }
}

