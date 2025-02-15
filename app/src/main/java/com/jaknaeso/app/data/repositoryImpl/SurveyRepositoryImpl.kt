package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import com.jaknaeso.app.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(private val surveyDataStore: SurveyDataStore) : SurveyRepository {
    override suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse> {
        return surveyDataStore.getSurveysHistory()
    }

    override suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse> {
        return surveyDataStore.getSurvey(bundleId)
    }

    override suspend fun postSurvey(surveyId: String, body: SurveySubmissionRequest): LoopyResult<Nothing> {
        return surveyDataStore.postSurvey(surveyId, body)
    }

    override suspend fun getSubmissionsReport(memberId: String, bundleId: String): LoopyResult<SurveyRecordsResponse> {
        return surveyDataStore.getSubmissionsReport(memberId, bundleId)
    }
}
