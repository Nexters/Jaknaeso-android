package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.data.authentication.ResponseHandler
import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.entity.ErrorData
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import com.jaknaeso.app.data.service.SurveyService
import javax.inject.Inject

class SurveyDataStoreImpl @Inject constructor(
    private val surveyService: SurveyService,
    private val responseHandler: ResponseHandler
) : SurveyDataStore {
    override suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse> {
        val retryResponse = responseHandler.safeApiCall(apiCall = { surveyService.getSurveysHistory() },
            onCompleteTokenRefresh = { null })
        val response = responseHandler.safeApiCall(apiCall = { surveyService.getSurveysHistory() },
            onCompleteTokenRefresh = { retryResponse })
        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }

    override suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse> {
        val retryResponse = responseHandler.safeApiCall(apiCall = { surveyService.getSurvey(bundleId) },
            onCompleteTokenRefresh = { null })
        val response = responseHandler.safeApiCall(apiCall = { surveyService.getSurvey(bundleId) },
            onCompleteTokenRefresh = { retryResponse })
        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }

    override suspend fun postSurvey(surveyId: String, body: SurveySubmissionRequest): LoopyResult<Nothing> {
        val retryResponse = responseHandler.safeApiCall(apiCall = { surveyService.postSurvey(surveyId, body) },
            onCompleteTokenRefresh = { null })
        val response = responseHandler.safeApiCall(apiCall = { surveyService.postSurvey(surveyId, body) },
            onCompleteTokenRefresh = { retryResponse })
        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }

    override suspend fun getSubmissionsReport(
        memberId: String,
        bundleId: String
    ): LoopyResult<SurveyRecordsResponse> {
        val retryResponse = responseHandler.safeApiCall(apiCall = {
            surveyService.getSubmissionsReport(
                memberId = memberId,
                bundleId = bundleId
            )
        },
            onCompleteTokenRefresh = { null })
        val response = responseHandler.safeApiCall(apiCall = {
            surveyService.getSubmissionsReport(
                memberId = memberId,
                bundleId = bundleId
            )
        },
            onCompleteTokenRefresh = { retryResponse })
        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }
}
