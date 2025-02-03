package com.jaknaeso.app.data.roomDB.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jaknaeso.app.data.roomDB.entity.BalanceQuestion

@Dao
interface BalanceQuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalanceQuestions(questions: List<BalanceQuestion>)

    @Query("SELECT * FROM balance_question_table WHERE roundIndex = :roundIndex")
    suspend fun getQuestionByRoundIndex(roundIndex: Int): BalanceQuestion?

}
