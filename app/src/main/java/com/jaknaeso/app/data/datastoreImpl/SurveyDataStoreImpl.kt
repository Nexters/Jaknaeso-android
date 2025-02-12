package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.service.SurveyService
import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import javax.inject.Inject

class SurveyDataStoreImpl @Inject constructor(
    private val surveyService: SurveyService,
) : SurveyDataStore {
    override suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse> {
        val result = LoopyResult<BundleRoundsResponse>(null, null, null)
        surveyService.getSurveysHistory().suspendMapSuccess {
            result.data = this
        }.suspendOnError {

        }
        return result
    }
}
