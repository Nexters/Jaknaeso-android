package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.datastore.LoginLocalDatastore
import com.jaknaeso.app.data.token.TokenManager
import javax.inject.Inject

class LoginLocalDatastoreImpl @Inject constructor(private val tokenManager: TokenManager) : LoginLocalDatastore {
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
