package com.jaknaeso.app.di

import com.jaknaeso.app.data.datastore.CharacterDatastore
import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.datastore.SurveyDataStore
import com.jaknaeso.app.data.datastoreImpl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {
    @Singleton
    @Binds
    fun provideLoginRemoteDatastore(loginRemoteDatastoreImpl: LoginRemoteDatastoreImpl): LoginRemoteDatastore

    @Singleton
    @Binds
    fun provideLoginLocalDatastore(loginLocalDatastoreImpl: LoginLocalDatastoreImpl): LoginLocalDatastore

    @Singleton
    @Binds
    fun provideSurveyDatastore(surveyDataStoreImpl: SurveyDataStoreImpl): SurveyDataStore

    @Singleton
    @Binds
    fun provideCharacterDatastore(characterDatastoreImpl: CharacterDatastoreImpl): CharacterDatastore
}
