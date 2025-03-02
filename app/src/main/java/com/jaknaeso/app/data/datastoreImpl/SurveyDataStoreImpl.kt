package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.ApiCallAdapter
import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.OnboardingSubmissionsInfoRequest
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import com.jaknaeso.app.data.entity.response.SurveyResponses
import com.jaknaeso.app.data.service.SurveyService
import javax.inject.Inject

class SurveyDataStoreImpl @Inject constructor(
    private val surveyService: SurveyService,
    private val apiCallAdapter: ApiCallAdapter
) : SurveyDataStore {


    override suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse>? {
        return apiCallAdapter.getApiResult { surveyService.getSurveysHistory() }
    }

    override suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse>? {
        return apiCallAdapter.getApiResult { surveyService.getSurvey(bundleId) }
    }

    override suspend fun postSurvey(surveyId: String, body: SurveySubmissionRequest): LoopyResult<Nothing>? {
        return apiCallAdapter.getApiResult { surveyService.postSurvey(surveyId, body) }
    }

    override suspend fun getSubmissionsReport(memberId: String, bundleId: String): LoopyResult<SurveyRecordsResponse>? {
        return apiCallAdapter.getApiResult {
            surveyService.getSubmissionsReport(
                memberId = memberId,
                bundleId = bundleId
            )
        }
    }

    override suspend fun getOnboarding(): LoopyResult<SurveyResponses>? {
        return apiCallAdapter.getApiResult { surveyService.getOnboarding() }
    }

    override suspend fun postOnboardingAnswers(body: OnboardingSubmissionsInfoRequest): LoopyResult<Nothing>? {
        return apiCallAdapter.getApiResult { surveyService.postOnboardingAnswer(body) }
    }
}
