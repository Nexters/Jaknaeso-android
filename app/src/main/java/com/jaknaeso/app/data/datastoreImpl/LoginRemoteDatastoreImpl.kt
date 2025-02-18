package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.RefreshTokenManager
import com.jaknaeso.app.data.datastore.LoginRemoteDatastore
import com.jaknaeso.app.data.entity.ErrorData
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.data.entity.response.MemberTokenResponse
import com.jaknaeso.app.data.service.LoginService
import com.skydoves.sandwich.exceptions.NoContentException
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class LoginRemoteDatastoreImpl @Inject constructor(
    private val loginService: LoginService,
    private val refreshTokenManager: RefreshTokenManager
) : LoginRemoteDatastore {
    override suspend fun getMemberToken(request: TokenRequest): LoopyResult<MemberTokenResponse>? {
        var result: LoopyResult<MemberTokenResponse>? = LoopyResult(result = null, data = null, error = null)
        try {
            suspend fun retryCall() {
                loginService.getMemberToken(request).suspendOnSuccess {
                    result = this.data
                }.suspendOnError {
                    result = LoopyResult(
                        result = ResponseResult.ERROR.name,
                        data = null,
                        error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                    )
                }
            }

            loginService.getMemberToken(request).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = LoopyResult(
                    result = ResponseResult.ERROR.name,
                    data = null,
                    error = ErrorData(code = this.response.code().toString(), message = "", data = null)
                )
                if (this.response.code() == 401) {
                    refreshTokenManager.handleTokenRefresh(
                        retryCall = suspend { retryCall() },
                        onRefreshFailed = {
                            result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                        }
                    )
                }
            }
        } catch (e: NoContentException) {
            result = LoopyResult(result = ResponseResult.SUCCESS.name, null, null)
        }
        return result
    }
}
