package com.jaknaeso.app.data.authentication

import com.google.gson.GsonBuilder
import com.jaknaeso.app.BuildConfig
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.TokenInfo
import com.jaknaeso.app.data.service.LoginService
import com.jaknaeso.app.data.token.TokenManager
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshTokenManagerImpl @Inject constructor(private val tokenManager: TokenManager) : RefreshTokenManager {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val interceptor = Interceptor { chain ->
        with(chain) {
            val token = tokenManager.getRefreshTokenForHeader()
            val newRequest = request().newBuilder()
                .header("Refresh-Token", "Bearer ${token}")
                .build()
            proceed(newRequest)
        }
    }
    val okHttp =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS).build()

    fun createWebService(baseUrl: String, okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    override suspend fun handleTokenRefresh(
        retryCall: suspend () -> Unit,
        onRefreshFailed: () -> Unit
    ) {
        refreshTokens().suspendOnSuccess {
            saveRefreshTokens(
                accessToken = this.data.data!!.accessToken,
                refreshToken = this.data.data!!.refreshToken
            )
            retryCall()
        }.suspendOnError {
            onRefreshFailed()
        }
    }

    override suspend fun refreshTokens(): ApiResponse<LoopyResult<TokenInfo>> {
        val response =
            createWebService(BuildConfig.BASE_URL, okHttp).create(LoginService::class.java).getRefreshToken()
        return response
    }

    override suspend fun saveRefreshTokens(accessToken: String, refreshToken: String) {
        tokenManager.saveAccessToken(accessToken)
        tokenManager.saveRefreshToken(refreshToken)
    }
}
