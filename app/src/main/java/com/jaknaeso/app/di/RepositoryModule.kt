package com.jaknaeso.app.di

import com.jaknaeso.app.data.repositoryImpl.*
import com.jaknaeso.app.domain.repository.*
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
    fun bindBalanceQuestionRepositoryImpl(repositoryImpl: BalanceQuestionRepositoryImpl): BalanceQuestionRepository

    @Binds
    @Singleton
    fun bindSurveyRepositoryImpl(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository

    @Binds
    @Singleton
    fun bindCharacterRepositoryImpl(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository
}
