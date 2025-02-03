package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.roomDB.entity.BalanceQuestion

interface BalanceQuestionRepository {
    suspend fun getQuestionByRoundIndex(roundIndex: Int): BalanceQuestion?
}
