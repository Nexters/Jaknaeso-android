package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.datastoreImpl.LoginLocalDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.data.entity.response.MemberTokenResponse
import com.jaknaeso.app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDatastore: LoginRemoteDatastore,
    private val loginLocalDatastore: LoginLocalDatastore
) :
    LoginRepository {
    override suspend fun getMemberToken(request: TokenRequest): LoopyResult<MemberTokenResponse>? {
        val result = loginRemoteDatastore.getMemberToken(request)
        return result
    }

    override suspend fun getAccessToken(): String? {
        return loginLocalDatastore.getAccessToken()
    }

    override suspend fun getRefreshToken(): String? {
        return loginLocalDatastore.getRefreshToken()
    }

    override suspend fun saveAccessToken(token: String) {
        loginLocalDatastore.saveAccessToken(token)
    }

    override suspend fun saveRefreshToken(token: String) {
        loginLocalDatastore.saveRefreshToken(token)
    }

    override suspend fun deleteAccessToken(token: String) {
        loginLocalDatastore.deleteAccessToken()
    }

    override suspend fun deleteRefreshToken(token: String) {
        loginLocalDatastore.deleteRefreshToken()
    }
}
