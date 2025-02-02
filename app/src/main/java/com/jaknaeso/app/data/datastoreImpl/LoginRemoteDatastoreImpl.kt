package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.Authenticator
import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.model.ApiResponse
import com.jaknaeso.app.data.model.ResponseResult
import com.jaknaeso.app.data.model.request.TokenRequest
import com.jaknaeso.app.data.model.response.MemberTokenResponse
import com.jaknaeso.app.data.service.LoginService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendMapSuccess
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class LoginRemoteDatastoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val authenticator: Authenticator
) : LoginRemoteDatastore {
    override suspend fun getMemberToken(request: TokenRequest): ApiResponse<MemberTokenResponse> {
        val result = ApiResponse<MemberTokenResponse>(result = null, data = null, error = null)
        loginService.getMemberToken(request).suspendMapSuccess {
            result.data = this
        }.suspendOnError {
            authenticator.handleApiError(
                rawMessage = this.message(),
                throwError = {
                    result.error = it
                    result.result = ResponseResult.ERROR.name
                },
                onCompleteTokenRefresh = {
                    loginService.getMemberToken(request).suspendOnSuccess { result.data = this.data }
                }
            )
        }
        return result
    }
}
