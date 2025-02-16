package com.jaknaeso.app.di

import com.jaknaeso.app.data.repositoryImpl.CharacterRepositoryImpl
import com.jaknaeso.app.data.repositoryImpl.LoginRepositoryImpl
import com.jaknaeso.app.data.repositoryImpl.MemberRepositoryImpl
import com.jaknaeso.app.data.repositoryImpl.SurveyRepositoryImpl
import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import com.jaknaeso.app.domain.repository.SurveyRepository
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
    fun bindSurveyRepositoryImpl(surveyRepositoryImpl: SurveyRepositoryImpl): SurveyRepository

    @Binds
    @Singleton
    fun bindCharacterRepositoryImpl(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    @Singleton
    fun bindMemberRepositoryImpl(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository
}
