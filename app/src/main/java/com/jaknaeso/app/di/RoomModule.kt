package com.jaknaeso.app.di

import android.content.Context
import androidx.room.Room
import com.jaknaeso.app.data.roomDB.dao.RoundDao
import com.jaknaeso.app.data.roomDB.RoundDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        callback: RoundDatabase.DatabaseCallback
    ): RoundDatabase {
        return Room.databaseBuilder(
            context,
            RoundDatabase::class.java,
            "round_database"
        ).addCallback(callback) // 🚀 DatabaseCallback 추가
            .build()
    }

    @Provides
    fun provideRoundDao(database: RoundDatabase): RoundDao {
        return database.roundDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseCallback(database: Provider<RoundDatabase>): RoundDatabase.DatabaseCallback {
        return RoundDatabase.DatabaseCallback(database)
    }
}

