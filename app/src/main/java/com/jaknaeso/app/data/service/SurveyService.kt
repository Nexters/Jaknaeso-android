package com.jaknaeso.app.data.service

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse
import com.jaknaeso.app.domain.entity.response.RoundQuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SurveyService {
    @GET("/api/v1/surveys/history")
    suspend fun getSurveysHistory(): Response<LoopyResult<BundleRoundsResponse>>

    @GET("/api/v1/surveys/{bundleId}")
    suspend fun getSurvey(@Path("bundleId") bundleId: String): Response<LoopyResult<RoundQuestionResponse>>
}
