package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.data.authentication.ResponseHandler
import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.service.LoginService
import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.request.TokenRequest
import com.jaknaeso.app.domain.entity.response.MemberTokenResponse
import javax.inject.Inject

class LoginRemoteDatastoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val responseHandler: ResponseHandler
) : LoginRemoteDatastore {
    override suspend fun getMemberToken(request: TokenRequest): LoopyApiResponse<LoopyResult<MemberTokenResponse>> {
        val retryResponse =
            responseHandler.safeApiCall(
                apiCall = { loginService.getMemberToken(request) },
                onCompleteTokenRefresh = { null })

        val response =
            responseHandler.safeApiCall(
                apiCall = { loginService.getMemberToken(request) },
                onCompleteTokenRefresh = { retryResponse })

        return response
    }
}
