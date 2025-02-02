package com.jaknaeso.app.di

import com.jaknaeso.app.data.datastore.LoginLocalDatastore
import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.datastoreImpl.LoginLocalDatastoreImpl
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
    fun provideLoginRemoteDatastore(loginRemoteDatastoreImpl: LoginRemoteDatastoreImpl): LoginRemoteDatastore

    @Singleton
    @Binds
    fun provideLoginLocalDatastore(loginLocalDatastoreImpl: LoginLocalDatastoreImpl): LoginLocalDatastore
}
