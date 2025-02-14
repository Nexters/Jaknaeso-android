package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.domain.repository.SurveyRepository
import javax.inject.Inject

class UpdateRoundsStateUseCase @Inject constructor(private val surveyRepository: SurveyRepository) {

    suspend operator fun invoke(surveyId: String, optionId: String, comment: String) {
        surveyRepository.postSurvey(
            surveyId = surveyId,
            body = SurveySubmissionRequest(optionId = optionId, comment = comment)
        )
    }
}
