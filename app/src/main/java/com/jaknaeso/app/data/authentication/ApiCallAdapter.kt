package com.jaknaeso.app.data.authentication

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import retrofit2.Response
import javax.inject.Inject

interface ApiCallAdapter {
    suspend fun <T> getApiResult(
        apiCall: suspend () -> Response<LoopyResult<T>>
    ): LoopyResult<T>?

    suspend fun handleApiCallError(error: Throwable, apiCall: suspend () -> Unit)
    suspend fun <T> retrySafeApiCall(apiCall: suspend () -> Response<LoopyResult<T>>): LoopyResult<T>?
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<LoopyResult<T>>): Result<LoopyResult<T>>
    fun extractResponseCode(responseString: String): Int
    fun extractResponseMessage(responseString: String): String
}

class ApiCallAdapterImpl @Inject constructor(private val refreshTokenManager: RefreshTokenManager) : ApiCallAdapter {
    override suspend fun <T> getApiResult(
        apiCall: suspend () -> Response<LoopyResult<T>>
    ): LoopyResult<T>? {
        suspend fun retryCall() {
            retrySafeApiCall { apiCall() }
        }
        try {
            safeApiCall { apiCall() }.onSuccess {
                return it
            }.onFailure {
                handleApiCallError(error = it, apiCall = { retryCall() })
            }
        } catch (e: Exception) {
            return LoopyResult(
                result = ResponseResult.ERROR.name,
                null,
                null
            )
        }
        return null
    }

    override suspend fun handleApiCallError(error: Throwable, apiCall: suspend () -> Unit) {
        val code = extractResponseCode(error.message ?: "")
        if (code == 401) {
            refreshTokenManager.handleTokenRefresh(
                retryCall = suspend { apiCall() },
                onRefreshFailed = { throw Exception(error.message) }
            )
        } else {
            throw Exception(error.message)
        }
    }

    override suspend fun <T> retrySafeApiCall(apiCall: suspend () -> Response<LoopyResult<T>>): LoopyResult<T>? {
        safeApiCall { apiCall() }.onSuccess {
            return it
        }.onFailure {
            throw Exception(it.message)
        }
        return null
    }

    override suspend fun <T> safeApiCall(apiCall: suspend () -> Response<LoopyResult<T>>): Result<LoopyResult<T>> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                return Result.success(
                    LoopyResult(
                        result = ResponseResult.SUCCESS.name,
                        data = response.body()?.data,
                        error = null
                    )
                )
            }
            return Result.failure(Throwable("code: ${response.code()}, message: ${response.message()}"))
        } catch (e: Exception) {
            return Result.failure(Throwable("code: 500, message: ${e}"))
        }
    }

    override fun extractResponseCode(responseString: String): Int {
        if (responseString.isBlank()) return 500  // 빈 문자열이면 기본값 500 반환

        val regex = """code:\s*(\d+)""".toRegex()
        return regex.find(responseString)?.groupValues?.get(1)?.toIntOrNull() ?: 500
    }

    override fun extractResponseMessage(responseString: String): String {
        if (responseString.isBlank()) return "Unknown Error"  // 빈 문자열이면 기본 메시지 반환

        val regex = """message:\s*(.+)""".toRegex()
        return regex.find(responseString)?.groupValues?.get(1)?.trim() ?: "Unknown Error"
    }
}
