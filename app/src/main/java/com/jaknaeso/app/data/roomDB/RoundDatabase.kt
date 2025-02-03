package com.jaknaeso.app.data.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jaknaeso.app.data.roomDB.dao.BalanceQuestionDao
import com.jaknaeso.app.data.roomDB.dao.RoundDao
import com.jaknaeso.app.data.roomDB.entity.BalanceQuestion
import com.jaknaeso.app.data.roomDB.entity.Round
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Round::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RoundDatabase : RoomDatabase() {
    abstract fun roundDao(): RoundDao
    abstract fun balanceQuestionDao(): BalanceQuestionDao
    class DatabaseCallback @Inject constructor(
        private val database: Provider<RoundDatabase>
    ) : RoomDatabase.Callback() {
        private val _isDatabaseInitialized = MutableStateFlow(false)
        val isDatabaseInitialized = _isDatabaseInitialized.asStateFlow()

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                val roundDao = database.get().roundDao()
                val balanceQuestionDao = database.get().balanceQuestionDao()
                populateInitialData(roundDao)
                populateInitialBalanceQuestions(balanceQuestionDao)
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

        private suspend fun populateInitialBalanceQuestions(balanceQuestionDao: BalanceQuestionDao) {
            val initialBalanceQuestions = listOf(
                BalanceQuestion(0, 0, "당신은 계획을 세우는 편인가요?", listOf("계획적", "즉흥적")),
                BalanceQuestion(1, 1, "도전과 안정을 선택해야 한다면?", listOf("도전", "안정")),
                BalanceQuestion(2, 2, "당신은 감정보다 논리를 더 중시하나요?", listOf("감성", "논리")),
                BalanceQuestion(3, 3, "일을 할 때, 효율과 완성도 중 어떤 것을 더 중요하게 생각하나요?", listOf("효율", "완성도")),
                BalanceQuestion(4, 4, "타인의 의견을 중요하게 생각하시나요?", listOf("개인 의견", "타인 의견")),
                BalanceQuestion(5, 5, "리더십과 팔로우십 중 어떤 것이 더 중요하다고 생각하나요?", listOf("리더십", "팔로우십")),
                BalanceQuestion(6, 6, "경쟁과 협력 중 무엇이 더 중요하다고 생각하나요?", listOf("경쟁", "협력")),
                BalanceQuestion(7, 7, "목표를 설정할 때, 현실적인 목표와 이상적인 목표 중 어느 쪽을 선호하시나요?", listOf("현실적 목표", "이상적 목표")),
                BalanceQuestion(8, 8, "사람을 볼 때, 신뢰와 능력 중 무엇을 더 중요하게 생각하나요?", listOf("신뢰", "능력")),
                BalanceQuestion(9, 9, "자유와 규율 중 어떤 것이 더 필요하다고 생각하나요?", listOf("자유", "규율")),
                BalanceQuestion(10, 10, "지식과 경험 중 무엇이 더 중요하다고 생각하나요?", listOf("지식", "경험")),
                BalanceQuestion(11, 11, "사회에서 정의와 관용 중 무엇이 더 중요하다고 생각하나요?", listOf("정의", "관용")),
                BalanceQuestion(12, 12, "빠른 변화와 안정된 환경 중 어떤 것을 더 선호하시나요?", listOf("변화", "안정")),
                BalanceQuestion(13, 13, "미래를 대비하는 것과 현재를 즐기는 것 중 어떤 것이 더 중요하다고 생각하나요?", listOf("미래 대비", "현재 즐기기")),
                BalanceQuestion(14, 14, "성공과 행복 중 무엇이 더 중요하다고 생각하시나요?", listOf("성공", "행복")),
            )
            balanceQuestionDao.insertBalanceQuestions(initialBalanceQuestions)
        }
    }
}

