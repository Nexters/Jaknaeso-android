package com.jaknaeso.app.di

import com.jaknaeso.app.data.authentication.ApiCallAdapter
import com.jaknaeso.app.data.authentication.ApiCallAdapterImpl
import com.jaknaeso.app.data.authentication.RefreshTokenManager
import com.jaknaeso.app.data.authentication.RefreshTokenManagerImpl
import com.jaknaeso.app.data.token.TokenManager
import com.jaknaeso.app.data.token.TokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthenticationModule {

    @Binds
    @Singleton
    fun bindTokenManager(tokenManagerImpl: TokenManagerImpl): TokenManager

    @Binds
    @Singleton
    fun bindRefreshTokenManager(refreshTokenManagerImpl: RefreshTokenManagerImpl): RefreshTokenManager

    @Binds
    @Singleton
    fun bindApiCalAdapter(apiCallAdapterImpl: ApiCallAdapterImpl): ApiCallAdapter
}
