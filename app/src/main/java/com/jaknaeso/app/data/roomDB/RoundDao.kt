package com.jaknaeso.app.data.roomDB

import androidx.room.*

@Dao
interface RoundDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRounds(rounds: List<Round>)

    @Update
    suspend fun updateRound(round: Round)

    @Query("SELECT * FROM round_table ORDER BY roundIndex ASC")
    suspend fun getAllRounds(): List<Round>

    @Query("SELECT * FROM round_table WHERE roundIndex = :roundIndex")
    suspend fun getRoundByIndex(roundIndex: Int): Round?

    @Query("UPDATE round_table SET isLocked = :isLocked, isCompleted = :isCompleted, isTodayQuestion = :isTodayQuestion WHERE roundIndex = :roundIndex")
    suspend fun updateRoundByIndex(roundIndex: Int, isLocked: Boolean, isCompleted: Boolean, isTodayQuestion: Boolean)

    @Query("DELETE FROM round_table")
    suspend fun deleteAllRounds()
}
