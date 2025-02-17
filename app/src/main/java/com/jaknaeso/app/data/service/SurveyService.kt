package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.OnboardingSubmissionsInfoRequest
import com.jaknaeso.app.data.entity.request.SurveySubmissionRequest
import com.jaknaeso.app.data.entity.response.BundleRoundsResponse
import com.jaknaeso.app.data.entity.response.RoundQuestionResponse
import com.jaknaeso.app.data.entity.response.SurveyRecordsResponse
import com.jaknaeso.app.data.entity.response.SurveyResponses
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface SurveyService {
    @GET("/api/v1/surveys/history")
    suspend fun getSurveysHistory(): ApiResponse<LoopyResult<BundleRoundsResponse>>

    @GET("/api/v1/surveys/{bundleId}")
    suspend fun getSurvey(@Path("bundleId") bundleId: String): ApiResponse<LoopyResult<RoundQuestionResponse>>

    @POST("/api/v1/surveys/{surveyId}/submission")
    suspend fun postSurvey(
        @Path("surveyId") surveyId: String,
        @Body body: SurveySubmissionRequest
    ): ApiResponse<LoopyResult<Nothing>>

    @GET("/api/v1/surveys/members/{memberId}/submissions")
    suspend fun getSubmissionsReport(
        @Path("memberId") memberId: String,
        @Query("bundleId") bundleId: String
    ): ApiResponse<LoopyResult<SurveyRecordsResponse>>

    @GET("/api/v1/surveys/onboarding")
    suspend fun getOnboarding(): ApiResponse<LoopyResult<SurveyResponses>>

    @POST("/api/v1/surveys/onboarding/submission")
    suspend fun postOnboardingAnswer(@Body body: OnboardingSubmissionsInfoRequest): ApiResponse<LoopyResult<Nothing>>
}
