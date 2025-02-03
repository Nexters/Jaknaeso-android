package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.roomDB.entity.Round
import kotlinx.coroutines.flow.Flow

interface RoundRepository {
    suspend fun insertRounds(rounds: List<Round>)
    suspend fun updateRound(round: Round)
    suspend fun getAllRounds(): Flow<List<Round>>
    suspend fun getRoundByIndex(roundIndex: Int): Round?
    suspend fun updateRoundByIndex(roundIndex: Int, isLocked: Boolean, isCompleted: Boolean, isTodayQuestion: Boolean)
    suspend fun deleteAllRounds()

}
