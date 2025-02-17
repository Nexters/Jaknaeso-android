package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.data.entity.response.MemberTokenResponse
import com.jaknaeso.app.data.entity.response.TokenInfo
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/auth/kakao-login/token")
    suspend fun getMemberToken(@Body request: TokenRequest): ApiResponse<LoopyResult<MemberTokenResponse>>

    @POST("/api/v1/auth/reissue")
    suspend fun getRefreshToken(): ApiResponse<LoopyResult<TokenInfo>>
}
