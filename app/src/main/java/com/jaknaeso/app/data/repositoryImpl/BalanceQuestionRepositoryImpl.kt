package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.roomDB.dao.BalanceQuestionDao
import com.jaknaeso.app.data.roomDB.entity.BalanceQuestion
import com.jaknaeso.app.domain.repository.BalanceQuestionRepository
import javax.inject.Inject

class BalanceQuestionRepositoryImpl @Inject constructor(private val balanceQuestionDao: BalanceQuestionDao) :
    BalanceQuestionRepository {
    override suspend fun getQuestionByRoundIndex(roundIndex: Int): BalanceQuestion? {
        return balanceQuestionDao.getQuestionByRoundIndex(roundIndex)
    }
}
