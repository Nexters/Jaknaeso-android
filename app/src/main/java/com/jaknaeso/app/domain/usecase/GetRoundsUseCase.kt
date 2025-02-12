package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.entity.ResponseResult
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.model.Round
import com.jaknaeso.app.domain.model.RoundBundle
import com.jaknaeso.app.domain.repository.RoundRepository
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRoundsUseCase @Inject constructor(
    private val roundRepository: RoundRepository,
    private val surveyRepository: SurveyRepository
) {
    val ROUNDS = 15
    suspend operator fun invoke(): Flow<RoundBundle?> {
        val data = surveyRepository.getSurveysHistory()
        var result: RoundBundle? = null
        val rounds = mutableListOf<Round>()
        if (data.result == ResponseResult.SUCCESS.name) {

            data.data?.surveyHistoryDetails?.map {
                rounds.add(Round(it.submissionId, QuestionState.PAST))
            }

            if (data.data?.isCompleted!!) {
                rounds.add(Round(roundId = data.data?.nextSurveyIndex!!, state = QuestionState.TODAY_COMPLETED))
            } else {
                rounds.add(Round(roundId = data.data?.nextSurveyIndex!!, state = QuestionState.TODAY_LOCKED))
            }

            for (i in rounds.size until ROUNDS) {
                rounds.add(Round(roundId = null, state = QuestionState.FUTURE))
            }
            result = RoundBundle(
                bundleId = data.data?.bundleId,
                rounds = rounds,
                isTodayRoundCompleted = data.data?.isCompleted ?: false
            )
        }
        return flow { emit(result) }
    }

    suspend fun getDummyData(): Flow<List<Round>> {
        return roundRepository.getAllRounds().map {
            it.map {
                Round(
                    roundId = it.roundIndex,
                    state = QuestionState.PAST
                )
            }
        }
    }
}
