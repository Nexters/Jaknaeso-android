package com.jaknaeso.app.data.authentication

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.TokenInfo

interface RefreshTokenManager {
    suspend fun handleTokenRefresh(
        retryCall: suspend () -> Unit,
        onRefreshFailed: () -> Unit
    )

    suspend fun refreshTokens(): Result<LoopyResult<TokenInfo>>

    suspend fun saveRefreshTokens(accessToken: String, refreshToken: String)
}
