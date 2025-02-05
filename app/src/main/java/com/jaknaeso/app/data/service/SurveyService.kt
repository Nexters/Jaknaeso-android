package com.jaknaeso.app.data.service

import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface SurveyService {
    @GET("/api/v1/surveys/history")
    suspend fun getSurveysHistory(): ApiResponse<BundleRoundsResponse>
}
