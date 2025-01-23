package com.jaknaeso.app.data.token

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    fun getAuthTokenForHeader(): String
    suspend fun getAuthToken(): Flow<String?>
    suspend fun getRememberedToken(): Flow<String?>
    suspend fun saveAuthToken(token: String)
    suspend fun saveRememberedToken(token: String)
}
