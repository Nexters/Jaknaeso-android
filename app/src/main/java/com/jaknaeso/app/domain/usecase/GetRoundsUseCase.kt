package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.model.Round
import com.jaknaeso.app.domain.model.RoundBundle
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRoundsUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    val ROUNDS = 15

    suspend operator fun invoke(): Flow<RoundBundle> {
        val data = surveyRepository.getSurveysHistory()

        return flow {
            if (data.result != ResponseResult.SUCCESS.name) {
                throw Exception(data.error?.message)
            }

            val surveyData = data.data ?: return@flow
            val rounds = prepareRounds(surveyData)

            emit(
                RoundBundle(
                    bundleId = surveyData.bundleId,
                    wholeRounds = rounds,
                    faceRounds = getFaceRounds(rounds.toList(), surveyData.nextSurveyIndex!!),
                    isTodayRoundCompleted = surveyData.isCompleted
                )
            )
        }
    }

    private fun prepareRounds(surveyData: BundleRoundsResponse): MutableList<Round> {
        val rounds = mutableListOf<Round>()

        // 과거 라운드 추가
        surveyData.surveyHistoryDetails?.forEachIndexed { index, item ->
            rounds.add(Round(item.submissionId, index, QuestionState.PAST))
        }

        val todayRoundIndex = surveyData.nextSurveyIndex!! - 1

        if (!surveyData.isCompleted) {
            // 오늘 라운드 업데이트
            rounds[todayRoundIndex] = Round(
                submissionId = surveyData.nextSurveyIndex,
                index = todayRoundIndex,
                state = QuestionState.TODAY_LOCKED
            )
        } else {
            // 오늘 라운드가 완료되었을 경우
            rounds.add(
                Round(
                    submissionId = null,
                    index = surveyData.nextSurveyIndex!!,
                    state = QuestionState.TODAY_COMPLETED
                )
            )
        }

        // 미래 라운드 추가
        for (index in surveyData.nextSurveyIndex!! + 1 until ROUNDS) {
            rounds.add(Round(submissionId = null, index = index, state = QuestionState.FUTURE))
        }

        return rounds
    }

    fun getFaceRounds(rounds: List<Round>, nextSurveyIndex: Int): List<Round> {
        if (nextSurveyIndex in 1..5) return rounds.subList(1, 6)
        if (nextSurveyIndex in 6..10) return rounds.subList(6, 11)
        else return rounds.subList(11, 16)
    }
}
