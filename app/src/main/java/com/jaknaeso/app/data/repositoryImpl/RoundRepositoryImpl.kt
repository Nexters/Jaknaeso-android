package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.roomDB.entity.Round
import com.jaknaeso.app.data.roomDB.dao.RoundDao
import com.jaknaeso.app.domain.repository.RoundRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RoundRepositoryImpl @Inject constructor(private val roundDao: RoundDao) : RoundRepository {
    override suspend fun insertRounds(rounds: List<Round>) {
        roundDao.insertRounds(rounds)
    }

    override suspend fun updateRound(round: Round) {
        roundDao.updateRound(round)
    }

    override suspend fun getAllRounds(): Flow<List<Round>> {
        return flow {
            emit(roundDao.getAllRounds())
        }
    }

    override suspend fun getRoundByIndex(roundIndex: Int): Round? {
        return roundDao.getRoundByIndex(roundIndex)
    }

    override suspend fun updateRoundByIndex(
        roundIndex: Int,
        isLocked: Boolean,
        isCompleted: Boolean,
        isTodayQuestion: Boolean
    ) {
        roundDao.updateRoundByIndex(roundIndex, isLocked, isCompleted, isTodayQuestion)
    }

    override suspend fun deleteAllRounds() {
        roundDao.deleteAllRounds()
    }
}

