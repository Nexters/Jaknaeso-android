package com.jaknaeso.app.data.authentication

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.TokenInfo
import com.skydoves.sandwich.ApiResponse

interface RefreshTokenManager {
    suspend fun handleTokenRefresh(
        retryCall: suspend () -> Unit,
        onRefreshFailed: () -> Unit
    )

    suspend fun refreshTokens(): ApiResponse<LoopyResult<TokenInfo>>

    suspend fun saveRefreshTokens(accessToken: String, refreshToken: String)
}
