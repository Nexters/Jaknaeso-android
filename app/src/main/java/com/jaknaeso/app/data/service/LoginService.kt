package com.jaknaeso.app.data.service

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.request.TokenRequest
import com.jaknaeso.app.domain.entity.response.MemberTokenResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/kakao-login/token")
    suspend fun getMemberToken(@Body request: TokenRequest): Response<LoopyResult<MemberTokenResponse>>

    @POST("/api/v1/auth/reissue")
    suspend fun getRefreshToken(): ApiResponse<LoopyResult<MemberTokenResponse>>
}
