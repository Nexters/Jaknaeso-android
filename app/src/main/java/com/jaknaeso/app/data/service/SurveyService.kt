package com.jaknaeso.app.data.service

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import retrofit2.Response
import retrofit2.http.GET

interface SurveyService {
    @GET("/api/v1/surveys/history")
    suspend fun getSurveysHistory(): Response<LoopyResult<BundleRoundsResponse>>
}
