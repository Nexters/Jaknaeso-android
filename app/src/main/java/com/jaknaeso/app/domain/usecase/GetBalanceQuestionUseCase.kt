package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.repository.BalanceQuestionRepository
import javax.inject.Inject

class GetBalanceQuestionUseCase @Inject constructor(private val balanceQuestionRepository: BalanceQuestionRepository) {
    suspend operator fun invoke(roundIndex: Int): com.jaknaeso.app.domain.model.BalanceQuestion? {
        val result = balanceQuestionRepository.getQuestionByRoundIndex(roundIndex)
        if (result != null) {
            return com.jaknaeso.app.domain.model.BalanceQuestion(
                id = result.id,
                roundIndex = result.roundIndex,
                question = result.question,
                options = result.options
            )
        }
        return null
    }
}
