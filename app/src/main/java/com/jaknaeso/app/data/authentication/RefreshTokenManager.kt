package com.jaknaeso.app.data.authentication

import com.jaknaeso.app.data.model.response.MemberTokenResponse
import com.skydoves.sandwich.ApiResponse

interface RefreshTokenManager {
    suspend fun refreshTokens(): ApiResponse<MemberTokenResponse>

    suspend fun saveRefreshTokens(accessToken: String, refreshToken: String)
}
