package com.jaknaeso.app.di

import com.google.gson.GsonBuilder
import com.jaknaeso.app.BuildConfig
import com.jaknaeso.app.data.service.LoginService
import com.jaknaeso.app.data.service.SurveyService
import com.jaknaeso.app.data.token.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    fun provideHeaderInterceptor(tokenManager: TokenManager): Interceptor {
        return Interceptor { chain ->
            with(chain) {
                val token = tokenManager.getAuthTokenForHeader()
                val newRequest = request().newBuilder()
                    .header("X-AUTH-TOKEN", "${token}")
                    .build()
                proceed(newRequest)
            }
        }
    }

    @Singleton
    @Provides
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideSurveyService(retrofit: Retrofit): SurveyService {
        return retrofit.create(SurveyService::class.java)
    }
}
