package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.OnboardingSubmissionsInfoRequest
import com.jaknaeso.app.data.entity.request.SurveyOptionSubmission
import com.jaknaeso.app.domain.model.OptionId
import com.jaknaeso.app.domain.model.SurveyId
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostOnBoardingRoundUseCase @Inject constructor(private val surveyRepository: SurveyRepository) {
    suspend operator fun invoke(answers: Map<SurveyId, OptionId>): Flow<Nothing?> {
        val body = OnboardingSubmissionsInfoRequest(submissionsInfo = answers.mapToSurveyOptionSubmission())
        val response = surveyRepository.postOnboardingAnswers(body = body)
        return flow {
            if (response.result == ResponseResult.SUCCESS.name) {
                emit(response.data)
            } else {
                throw Exception(response.error?.message)
            }
        }
    }

    fun Map<String, String>.mapToSurveyOptionSubmission(): List<SurveyOptionSubmission> {
        return this.entries.map {
            SurveyOptionSubmission(surveyId = it.key, optionId = it.value)
        }
    }
}
