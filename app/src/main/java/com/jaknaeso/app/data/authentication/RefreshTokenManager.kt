package com.jaknaeso.app.data.authentication

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.MemberTokenResponse
import com.skydoves.sandwich.ApiResponse

interface RefreshTokenManager {
    suspend fun refreshTokens(): ApiResponse<LoopyResult<MemberTokenResponse>>

    suspend fun saveRefreshTokens(accessToken: String, refreshToken: String)
}
