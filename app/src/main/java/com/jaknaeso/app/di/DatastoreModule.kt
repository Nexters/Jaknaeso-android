package com.jaknaeso.app.di

import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.datastoreImpl.LoginRemoteDatastoreImpl
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
    fun provideLoginDatastore(loginDatastoreImpl: LoginRemoteDatastoreImpl): LoginRemoteDatastore
}
