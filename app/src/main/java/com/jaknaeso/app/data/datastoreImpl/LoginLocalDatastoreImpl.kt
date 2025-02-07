package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.token.TokenManager
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface LoginLocalDatastore {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun deleteAccessToken()
    suspend fun deleteRefreshToken()
}

class LoginLocalDatastoreImpl @Inject constructor(private val tokenManager: TokenManager) : LoginLocalDatastore {
    override suspend fun getAccessToken(): String? {
        return tokenManager.getAccessToken().first()
    }

    override suspend fun getRefreshToken(): String? {
        return tokenManager.getRefreshToken().first()
    }

    override suspend fun saveAccessToken(token: String) {
        tokenManager.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        tokenManager.saveRefreshToken(token)
    }

    override suspend fun deleteAccessToken() {
        tokenManager.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken() {
        tokenManager.deleteRefreshToken()
    }
}
