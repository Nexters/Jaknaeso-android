package com.jaknaeso.app.data.authentication

import com.google.gson.GsonBuilder
import com.jaknaeso.app.BuildConfig
import com.jaknaeso.app.data.service.LoginService
import com.jaknaeso.app.data.token.TokenManager
import com.jaknaeso.app.domain.entity.response.MemberTokenResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

fun createWebService(baseUrl: String, okHttp: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttp)
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
}

class RefreshTokenManagerImpl @Inject constructor(private val tokenManager: TokenManager) : RefreshTokenManager {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val okHttp =
        OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS).build()

    override suspend fun refreshTokens(): ApiResponse<MemberTokenResponse> {
        val response =
            createWebService(BuildConfig.BASE_URL, okHttp).create(LoginService::class.java).getRefreshToken()
        return response
    }

    override suspend fun saveRefreshTokens(accessToken: String, refreshToken: String) {
        tokenManager.saveAccessToken(accessToken)
        tokenManager.saveRefreshToken(refreshToken)
    }
}
