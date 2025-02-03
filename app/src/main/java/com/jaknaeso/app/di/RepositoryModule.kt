package com.jaknaeso.app.di

import com.jaknaeso.app.data.repositoryImpl.BalanceQuestionRepositoryImpl
import com.jaknaeso.app.data.repositoryImpl.LoginRepositoryImpl
import com.jaknaeso.app.data.repositoryImpl.RoundRepositoryImpl
import com.jaknaeso.app.domain.repository.BalanceQuestionRepository
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.domain.repository.RoundRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindLoginRepositoryImpl(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    fun bindRoundRepositoryImpl(repositoryImpl: RoundRepositoryImpl): RoundRepository

    @Binds
    @Singleton
    fun BalanceQuestionRepositoryImpl(repositoryImpl: BalanceQuestionRepositoryImpl): BalanceQuestionRepository
}
