package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.model.request.TokenRequest
import com.jaknaeso.app.data.model.response.MemberTokenResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/kakao-login")
    suspend fun getMemberToken(@Body request: TokenRequest): ApiResponse<MemberTokenResponse>

    @POST("/api/v1/auth/reissue")
    suspend fun getRefreshToken(): ApiResponse<MemberTokenResponse>
}
