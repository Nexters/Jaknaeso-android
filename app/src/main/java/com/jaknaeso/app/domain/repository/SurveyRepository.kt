package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.jaknaeso.app.domain.entity.response.RoundQuestionResponse

interface SurveyRepository {
    suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse>
    suspend fun getSurvey(bundleId: String): LoopyResult<RoundQuestionResponse>
}
