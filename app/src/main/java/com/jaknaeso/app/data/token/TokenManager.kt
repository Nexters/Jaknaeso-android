package com.jaknaeso.app.data.token

import kotlinx.coroutines.flow.Flow

interface TokenManager {
    fun getAuthTokenForHeader(): String
    fun getRefreshTokenForHeader(): String
    suspend fun getAccessToken(): Flow<String?>
    suspend fun getRefreshToken(): Flow<String?>
    suspend fun getMemberId(): Flow<String?>
    suspend fun getIsOnBoardingCompleted(): Flow<Boolean?>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveMemberId(memberId: String)
    suspend fun saveIsOnBoardingCompleted(value: Boolean)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteMemberId()
    suspend fun deleteIsOnBoardingCompleted()
}
