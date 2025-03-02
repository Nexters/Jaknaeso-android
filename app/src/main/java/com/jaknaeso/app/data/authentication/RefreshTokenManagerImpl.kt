package com.jaknaeso.app.data.authentication

import com.google.gson.GsonBuilder
import com.jaknaeso.app.BuildConfig
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.TokenInfo
import com.jaknaeso.app.data.service.LoginService
import com.jaknaeso.app.data.token.TokenManager
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
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    override suspend fun handleTokenRefresh(
        retryCall: suspend () -> Unit,
        onRefreshFailed: () -> Unit
    ) {
        refreshTokens().onSuccess {
            if (it.data != null) {
                saveRefreshTokens(
                    accessToken = it.data!!.accessToken,
                    refreshToken = it.data!!.refreshToken
                )
                retryCall()
            }
        }.onFailure {
            onRefreshFailed()
        }
    }

    override suspend fun refreshTokens(): Result<LoopyResult<TokenInfo>> {
        val response =
            createWebService(BuildConfig.BASE_URL, okHttp).create(LoginService::class.java).getRefreshToken()
        return response
    }

    override suspend fun saveRefreshTokens(accessToken: String, refreshToken: String) {
        tokenManager.saveAccessToken(accessToken)
        tokenManager.saveRefreshToken(refreshToken)
    }
}
