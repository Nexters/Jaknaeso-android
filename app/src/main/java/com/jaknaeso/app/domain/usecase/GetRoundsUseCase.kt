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

    operator fun invoke(): Flow<RoundBundle> {
        return flow {
            val response = surveyRepository.getSurveysHistory()
            if (response?.result == ResponseResult.ERROR.name) {
                throw Exception(response.error?.message)
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                throw Exception(response.result)
            }

            val surveyData = response?.data ?: return@flow
            val rounds = prepareRounds(surveyData)
            emit(
                RoundBundle(
                    bundleId = surveyData.bundleId,
                    wholeRounds = rounds,
                    faceRounds = getFaceRounds(rounds.toList(), surveyData.nextSurveyIndex!!),
                    isTodayRoundCompleted = surveyData.isCompleted,
                    remainRound = calculateRemainRounds(surveyData.nextSurveyIndex, surveyData.isCompleted)
                )
            )
        }
    }

    private fun prepareRounds(surveyData: BundleRoundsResponse): MutableList<Round> {
        val rounds = mutableListOf<Round>()

        // 과거 라운드 추가
        surveyData.surveyHistoryDetails?.forEachIndexed { index, item ->
            if (surveyData.isCompleted && surveyData.surveyHistoryDetails.lastIndex == index) { //오늘 완료된 것
                rounds.add(Round(item.submissionId, index + 1, QuestionState.TODAY_COMPLETED))
            } else {
                rounds.add(Round(item.submissionId, index + 1, QuestionState.PAST))
            }
        }

        if (!surveyData.isCompleted) {
            // 오늘의 미완료 라운드 업데이트
            rounds.add(
                Round(
                    submissionId = null,
                    index = surveyData.nextSurveyIndex!!,
                    state = QuestionState.TODAY_LOCKED
                )
            )
        }

        // 미래 라운드 추가
        for (index in surveyData.nextSurveyIndex!! + 1 until ROUNDS + 1) {
            rounds.add(Round(submissionId = null, index = index, state = QuestionState.FUTURE))
        }

        return rounds
    }

    fun getFaceRounds(rounds: List<Round>, nextSurveyIndex: Int): List<Round> {
        if (nextSurveyIndex in 1..5) return rounds.subList(0, 5)
        if (nextSurveyIndex in 6..10) return rounds.subList(5, 10)
        else return rounds.subList(10, 15)
    }

    fun calculateRemainRounds(nextSurveyIndex: Int, isCompleted: Boolean): Int {
        if (isCompleted) return 15 - nextSurveyIndex
        else return 15 - nextSurveyIndex + 1
    }
}
