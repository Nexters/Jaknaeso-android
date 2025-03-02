package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.ApiCallAdapter
import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.data.entity.response.MemberTokenResponse
import com.jaknaeso.app.data.service.LoginService
import javax.inject.Inject

class LoginRemoteDatastoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val apiCallAdapter: ApiCallAdapter
) : LoginRemoteDatastore {
    override suspend fun getMemberToken(request: TokenRequest): LoopyResult<MemberTokenResponse>? {
        return apiCallAdapter.getApiResult { loginService.getMemberToken(request) }
    }
}
