package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import retrofit2.Response
import retrofit2.http.*

interface SurveyService {
    @GET("/api/v1/surveys/history")
    suspend fun getSurveysHistory(): Response<LoopyResult<BundleRoundsResponse>>

    @GET("/api/v1/surveys/{bundleId}")
    suspend fun getSurvey(@Path("bundleId") bundleId: String): Response<LoopyResult<RoundQuestionResponse>>

    @POST("/api/v1/surveys/{surveyId}/submission")
    suspend fun postSurvey(
        @Path("surveyId") surveyId: String,
        @Body body: SurveySubmissionRequest
    ): Response<LoopyResult<Nothing>>

    @GET("/api/v1/surveys/members/{memberId}/submissions")
    suspend fun getSubmissionsReport(
        @Path("memberId") memberId: String,
        @Query("bundleId") bundleId: String
    ): Response<LoopyResult<SurveyRecordsResponse>>
}
