package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse

interface SurveyDataStore {
    suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse>
    suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse>
    suspend fun postSurvey(surveyId: String, body: SurveySubmissionRequest): LoopyResult<Nothing>
}
