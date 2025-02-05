package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.Authenticator
import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.service.SurveyService
import com.jaknaeso.app.domain.entity.ApiResponse
import com.jaknaeso.app.domain.entity.ResponseResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import javax.inject.Inject

class SurveyDataStoreImpl @Inject constructor(
    private val surveyService: SurveyService,
    private val authenticator: Authenticator
) : SurveyDataStore {
    override suspend fun getSurveysHistory(): ApiResponse<BundleRoundsResponse> {
        val result = ApiResponse<BundleRoundsResponse>(null, null, null)
        surveyService.getSurveysHistory().suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                throwError = {
                    result.error = it
                    result.result = ResponseResult.ERROR.name
                },
                onCompleteTokenRefresh = {
                    surveyService.getSurveysHistory().suspendMapSuccess {
                        result.data = this
                    }
                }
            )
        }
        return result
    }
}
