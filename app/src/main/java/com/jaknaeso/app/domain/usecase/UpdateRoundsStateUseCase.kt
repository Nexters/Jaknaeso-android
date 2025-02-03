package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.repository.RoundRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class UpdateRoundsStateUseCase @Inject constructor(private val roundRepository: RoundRepository) {
    suspend operator fun invoke(roundIndex: Int) {
        updateCurrentRound(roundIndex)
        updateNextRound(roundIndex)
    }

    private suspend fun updateCurrentRound(roundIndex: Int) {
        roundRepository.updateRoundByIndex(
            roundIndex = roundIndex,
            isLocked = true,
            isCompleted = true,
            isTodayQuestion = false
        )

    }

    private suspend fun updateNextRound(roundIndex: Int) {
        roundRepository.getAllRounds().collectLatest {
            val last_index = it.size - 1
            if (roundIndex < last_index) {
                roundRepository.updateRoundByIndex(
                    roundIndex = roundIndex + 1,
                    isLocked = false,
                    isCompleted = false,
                    isTodayQuestion = false
                )
            }
        }
    }
}
