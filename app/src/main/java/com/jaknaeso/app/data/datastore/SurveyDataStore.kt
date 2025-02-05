package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.domain.entity.ApiResponse
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse

interface SurveyDataStore {
    suspend fun getSurveysHistory(): ApiResponse<BundleRoundsResponse>
}
