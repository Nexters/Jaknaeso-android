package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRoundsStateUseCase @Inject constructor(private val surveyRepository: SurveyRepository) {

    suspend operator fun invoke(surveyId: String, optionId: String, comment: String): Flow<Nothing?> {
        val response = surveyRepository.postSurvey(
            surveyId = surveyId,
            body = SurveySubmissionRequest(optionId = optionId, comment = comment)
        )
        if (response?.result == ResponseResult.SUCCESS.name) {
            return flow { emit(response.data) }
        } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
            return flow { throw Exception(response.result) }
        } else {
            return flow { throw Exception(response?.error?.message) }
        }
    }
}
