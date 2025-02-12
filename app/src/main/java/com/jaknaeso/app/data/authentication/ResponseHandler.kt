package com.jaknaeso.app.data.authentication

import android.util.Log
import com.jaknaeso.app.data.token.TokenManager
import com.jaknaeso.app.domain.entity.Error
import com.jaknaeso.app.domain.entity.LoopyResult
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

val UNKNOWN_CODE = 500
val UNKNOWN_MESSAGE = "예기치 못한 오류입니다:("

sealed class LoopyApiResponse<out T> {
    data class Success<T>(val data: T) : LoopyApiResponse<T>()
    data class Error(val code: Int, val message: String) : LoopyApiResponse<Nothing>()
}

class ResponseHandler @Inject constructor(
    private val tokenManager: TokenManager,
    private val refreshTokenManager: RefreshTokenManager
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>,
        onCompleteTokenRefresh: suspend () -> LoopyApiResponse<T>?
    ): LoopyApiResponse<T> {
        return try {
            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return LoopyApiResponse.Success(body)
                } else {
                    return LoopyApiResponse.Error(response.code(), "Response body is null")
                }
            } else {
                Json.decodeFromString<LoopyResult<Any>>(response.raw().message).apply {
                    this.error?.handleTokenExpiredError { error ->
                        refreshToken(onRefreshSuccess = { onCompleteTokenRefresh() }, onRefreshFail = {
                            LoopyApiResponse.Error(it?.code ?: UNKNOWN_CODE, it?.message ?: UNKNOWN_MESSAGE)
                        })
                    }
                }
            }
            LoopyApiResponse.Error(response.code(), response.message())

        } catch (e: HttpException) {
            LoopyApiResponse.Error(e.code(), e.message())
        } catch (e: IOException) {
            LoopyApiResponse.Error(UNKNOWN_CODE, e.message ?: UNKNOWN_MESSAGE)
        } catch (e: Exception) {
            LoopyApiResponse.Error(UNKNOWN_CODE, e.message ?: UNKNOWN_MESSAGE)
        }
    }

    private suspend fun refreshToken(
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
                            accessToken = responseBody.data?.accessToken ?: "",
                            refreshToken = responseBody.data?.refreshToken ?: ""
                        )
                        onRefreshSuccess()
                    }
                }
                .suspendOnError {
                    if (this.statusCode.code == 401) {
                        Log.e("Authenticator", "토큰 리프레싱 실패")
                        onRefreshFail(Json.decodeFromString<LoopyResult<Any>>(this.message()).error)
                    }
                }
        } else {
            Log.d("Authenticator", "리프레시 토큰 값이 null입니다.")
        }
    }

    private suspend fun updateAllTokens(accessToken: String, refreshToken: String) {
        tokenManager.deleteAccessToken()
        tokenManager.deleteRefreshToken()
        refreshTokenManager.saveRefreshTokens(accessToken, refreshToken)
    }

    private suspend inline fun Error.handleTokenExpiredError(
        crossinline onResult: suspend Error.(message: Error) -> Unit,
    ): Error {
        if (this.code == 401) {
            onResult(this)
        }
        return this
    }
}
