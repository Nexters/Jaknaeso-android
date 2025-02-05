package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.domain.entity.ApiResponse
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse

interface SurveyRepository {
    suspend fun getSurveysHistory(): ApiResponse<BundleRoundsResponse>
}
