package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.RefreshTokenManager
import com.jaknaeso.app.data.datastore.MemberDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import com.jaknaeso.app.data.service.MemberService
import com.jaknaeso.app.data.token.TokenManager
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import javax.inject.Inject

class MemberDatastoreImpl @Inject constructor(
    private val memberService: MemberService,
    private val refreshTokenManager: RefreshTokenManager,
    private val tokenManager: TokenManager
) : MemberDatastore {
    override suspend fun getMember(memberId: String): LoopyResult<MemberResponse>? {
        var result: LoopyResult<MemberResponse>? = LoopyResult(result = null, data = null, error = null)
        suspend fun retryCall() {
            memberService.getMember(memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
            }
        }

        memberService.getMember(memberId).suspendOnSuccess {
            result = this.data
        }.suspendOnError {
            result = this.response.body()
            if (this.response.code() == 401) {
                refreshTokenManager.handleTokenRefresh(
                    retryCall = suspend { retryCall() },
                    onRefreshFailed = {
                        result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                    }
                )
            }
        }
        return result
    }

    override suspend fun deleteMember(memberId: String): LoopyResult<Nothing?>? {
        var result: LoopyResult<Nothing?>? = LoopyResult(result = null, data = null, error = null)
        suspend fun retryCall() {
            memberService.deleteMember(memberId).suspendOnSuccess {
                result = this.data
            }.suspendOnError {
                result = this.response.body()
            }
        }

        memberService.deleteMember(memberId).suspendOnSuccess {
            result = this.data
        }.suspendOnError {
            result = this.response.body()
            if (this.response.code() == 401) {
                refreshTokenManager.handleTokenRefresh(
                    retryCall = suspend { retryCall() },
                    onRefreshFailed = {
                        result = LoopyResult(result = ResponseResult.REFRESH_FAILED.name, data = null, error = null)
                    }
                )
            }
        }
        return result
    }

    override suspend fun saveMemberId(memberId: String) {
        tokenManager.saveMemberId(memberId)
    }

    override suspend fun deleteMemberId() {
        tokenManager.deleteMemberId()
    }
}
