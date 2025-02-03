package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.repository.RoundRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class JudgeAllRoundCompletedUseCase @Inject constructor(private val roundRepository: RoundRepository) {
    suspend operator fun invoke(): Boolean {
        var result = false
        roundRepository.getAllRounds().collectLatest {
            val completedRounds = it.filter { it.isCompleted }
            if (completedRounds.size == it.size) {
                result = true
            }
        }
        return result
    }
}
