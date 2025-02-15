package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.OnboardingSubmissionsInfoRequest
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import com.jaknaeso.app.data.entity.response.SurveyResponses

interface SurveyDataStore {
    suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse>
    suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse>
    suspend fun postSurvey(surveyId: String, body: SurveySubmissionRequest): LoopyResult<Nothing>
    suspend fun getSubmissionsReport(memberId: String, bundleId: String): LoopyResult<SurveyRecordsResponse>
    suspend fun getOnboarding(): LoopyResult<SurveyResponses>
    suspend fun postOnboardingAnswers(body: OnboardingSubmissionsInfoRequest): LoopyResult<Nothing>
}
