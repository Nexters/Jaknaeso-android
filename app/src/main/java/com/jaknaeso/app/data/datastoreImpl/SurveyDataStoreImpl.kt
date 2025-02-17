package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.RefreshTokenManager
import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.OnboardingSubmissionsInfoRequest
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import com.jaknaeso.app.data.entity.response.SurveyResponses
import com.jaknaeso.app.data.service.SurveyService
import com.skydoves.sandwich.exceptions.NoContentException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class SurveyDataStoreImpl @Inject constructor(
    private val surveyService: SurveyService,
    private val refreshTokenManager: RefreshTokenManager
) : SurveyDataStore {


    override suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse>? {
        var result: LoopyResult<BundleRoundsResponse>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                surveyService.getSurveysHistory().suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = this.response.body()
                }
            }

            surveyService.getSurveysHistory().suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse>? {
        var result: LoopyResult<RoundQuestionResponse>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                surveyService.getSurvey(bundleId).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = this.response.body()
                }
            }

            surveyService.getSurvey(bundleId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun postSurvey(surveyId: String, body: SurveySubmissionRequest): LoopyResult<Nothing>? {
        var result: LoopyResult<Nothing>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                surveyService.postSurvey(surveyId, body).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = this.response.body()
                }
            }

            surveyService.postSurvey(surveyId, body).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun getSubmissionsReport(memberId: String, bundleId: String): LoopyResult<SurveyRecordsResponse>? {
        var result: LoopyResult<SurveyRecordsResponse>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                surveyService.getSubmissionsReport(memberId = memberId, bundleId = bundleId).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = this.response.body()
                }
            }

            surveyService.getSubmissionsReport(memberId = memberId, bundleId = bundleId)
                .suspendOnSuccess { result = this.data }.suspendOnError {
                    result = this.response.body()
                    if (this.response.code() == 401) {
                        refreshTokenManager.handleTokenRefresh(
                            retryCall = suspend { retryCall() },
                            onRefreshFailed = {
                                result =
                                    LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                            }
                        )
                    }
                }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun getOnboarding(): LoopyResult<SurveyResponses>? {
        var result: LoopyResult<SurveyResponses>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                surveyService.getOnboarding().suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = this.response.body()
                }
            }

            surveyService.getOnboarding()
                .suspendOnSuccess { result = this.data }.suspendOnError {
                    result = this.response.body()
                    if (this.response.code() == 401) {
                        refreshTokenManager.handleTokenRefresh(
                            retryCall = suspend { retryCall() },
                            onRefreshFailed = {
                                result =
                                    LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                            }
                        )
                    }
                }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }

    override suspend fun postOnboardingAnswers(body: OnboardingSubmissionsInfoRequest): LoopyResult<Nothing>? {
        var result: LoopyResult<Nothing>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                surveyService.postOnboardingAnswer(body).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = this.response.body()
                }
            }

            surveyService.postOnboardingAnswer(body)
                .suspendOnSuccess { result = this.data }.suspendOnError {
                    result = this.response.body()
                    if (this.response.code() == 401) {
                        refreshTokenManager.handleTokenRefresh(
                            retryCall = suspend { retryCall() },
                            onRefreshFailed = {
                                result =
                                    LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                            }
                        )
                    }
                }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }
}
