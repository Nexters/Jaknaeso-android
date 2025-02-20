package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.token.TokenManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface LoginLocalDatastore {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getIsOnBoardingCompleted(): Boolean?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveIsOnBoardingCompleted(value: Boolean)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
    suspend fun deleteIsOnBoardingCompleted()
}

class LoginLocalDatastoreImpl @Inject constructor(private val tokenManager: TokenManager) : LoginLocalDatastore {
    override suspend fun getAccessToken(): String? {
        return tokenManager.getAccessToken().first()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenManager.getRefreshToken().first()
    }

    override suspend fun getIsOnBoardingCompleted(): Boolean? {
        return tokenManager.getIsOnBoardingCompleted().first()
    }

    override suspend fun saveAccessToken(token: String) {
        tokenManager.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        tokenManager.saveRefreshToken(token)
    }

    override suspend fun saveIsOnBoardingCompleted(value: Boolean) {
        tokenManager.saveIsOnBoardingCompleted(value)
    }

    override suspend fun deleteAccessToken() {
        tokenManager.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken() {
        tokenManager.deleteRefreshToken()
    }

    override suspend fun deleteIsOnBoardingCompleted() {
        tokenManager.deleteIsOnBoardingCompleted()
    }
}
