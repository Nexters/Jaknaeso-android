package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.jaknaeso.app.domain.entity.response.RoundQuestionResponse
import com.jaknaeso.app.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(private val surveyDataStore: SurveyDataStore) : SurveyRepository {
    override suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse> {
        return surveyDataStore.getSurveysHistory()
    }

    override suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse> {
        return surveyDataStore.getSurvey(bundleId)
    }
}
