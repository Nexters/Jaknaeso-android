package com.jaknaeso.app.data.datastore

interface LoginLocalDatastore {
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
}
