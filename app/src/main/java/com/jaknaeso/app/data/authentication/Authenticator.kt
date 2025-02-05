package com.jaknaeso.app.data.authentication

import com.jaknaeso.app.domain.entity.Error

interface Authenticator {
    // API 에러를 처리하고, 토큰 갱신이 필요한 경우 처리하는 함수
    suspend fun handleApiError(
        rawMessage: String,
        throwError: (i: Error) -> Unit,
        onCompleteTokenRefresh: suspend () -> Unit
    )

    // 토큰 갱신 처리 함수
    suspend fun refreshToken(
        onRefreshSuccess: suspend () -> Unit,
        onRefreshFail: (errorMessage: Error?) -> Unit
    )

    // 모든 토큰을 업데이트하는 함수
    suspend fun updateAllTokens(accessToken: String, refreshToken: String)
}
