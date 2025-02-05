package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.domain.entity.ApiResponse
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.jaknaeso.app.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(private val surveyDataStore: SurveyDataStore) : SurveyRepository {
    override suspend fun getSurveysHistory(): ApiResponse<BundleRoundsResponse> {
        return surveyDataStore.getSurveysHistory()
    }

}
