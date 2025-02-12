package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.data.authentication.ResponseHandler
import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.service.SurveyService
import com.jaknaeso.app.domain.entity.ErrorData
import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.ResponseResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
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
}
