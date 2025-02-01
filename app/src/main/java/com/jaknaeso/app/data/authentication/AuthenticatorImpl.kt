package com.jaknaeso.app.data.authentication

import android.util.Log
import com.jaknaeso.app.data.model.ApiResponse
import com.jaknaeso.app.data.model.Error
import com.jaknaeso.app.data.model.ErrorCode
import com.jaknaeso.app.data.token.TokenManager
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthenticatorImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenManager: RefreshTokenManager
) : Authenticator {
    override suspend fun handleApiError(
        rawMessage: String,
        throwError: (i: Error) -> Unit,
        onCompleteTokenRefresh: suspend () -> Unit
    ) {
        Json.decodeFromString<ApiResponse<Any>>(rawMessage).apply {
            this.error?.handleTokenExpiredError { error ->
                refreshToken(onRefreshSuccess = { onCompleteTokenRefresh() }, onRefreshFail = { throwError(error) })
            }
            this.error?.handleGeneralError { error ->
                throwError(error)
            }
        }
    }

    override suspend fun refreshToken(
        onRefreshSuccess: suspend () -> Unit,
        onRefreshFail: (errorMessage: Error?) -> Unit
    ) {
        val rememberedToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }
        if (rememberedToken != null) {
            refreshTokenManager.refreshTokens()
                .suspendOnSuccess {
                    val responseBody = this.response.body()
                    if (responseBody != null) {
                        Log.d("Authenticator", "토큰 리프레싱 성공")
                        updateAllTokens(
                            accessToken = responseBody.accessToken,
                            refreshToken = responseBody.refreshToken
                        )
                        onRefreshSuccess()
                    }
                }
                .suspendOnError {
                    if (this.statusCode.code == 401) {
                        Log.e("Authenticator", "토큰 리프레싱 실패")
                        onRefreshFail(Json.decodeFromString<ApiResponse<Any>>(this.message()).error)
                    }
                }
        } else {
            Log.d("Authenticator", "리프레시 토큰 값이 null입니다.")
        }
    }

    override suspend fun updateAllTokens(accessToken: String, refreshToken: String) {
        tokenManager.deleteAccessToken()
        tokenManager.deleteRefreshToken()
        refreshTokenManager.saveRefreshTokens(accessToken, refreshToken)
    }

    suspend inline fun Error.handleTokenExpiredError(
        crossinline onResult: suspend Error.(message: Error) -> Unit,
    ): Error {
        if (this.code.name == ErrorCode.E401.name) {
            onResult(this)
        }
        return this
    }

    suspend inline fun Error.handleGeneralError(
        crossinline onResult: suspend Error.(message: Error) -> Unit,
    ): Error {
        if (this.code.name != ErrorCode.E401.name) {
            onResult(this)
        }
        return this
    }
}
