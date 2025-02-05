package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.model.Round
import com.jaknaeso.app.domain.repository.RoundRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRoundsUseCase @Inject constructor(private val roundRepository: RoundRepository) {
    suspend operator fun invoke(): Flow<List<Round>> {
        return roundRepository.getAllRounds().map {
            it.map {
                Round(
                    roundIndex = it.roundIndex,
                    isLocked = it.isLocked,
                    isCompleted = it.isCompleted,
                    isTodayQuestion = it.isTodayQuestion
                )
            }
        }

    }
}
