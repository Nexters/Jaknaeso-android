package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.data.authentication.ResponseHandler
import com.jaknaeso.app.data.datastore.MemberDatastore
import com.jaknaeso.app.data.entity.ErrorData
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import com.jaknaeso.app.data.service.MemberService
import com.jaknaeso.app.data.token.TokenManager
import javax.inject.Inject

class MemberDatastoreImpl @Inject constructor(
    private val memberService: MemberService,
    private val responseHandler: ResponseHandler,
    private val tokenManager: TokenManager
) : MemberDatastore {
    override suspend fun getMember(memberId: String): LoopyResult<MemberResponse>? {
        val retryResponse =
            responseHandler.safeApiCall(
                apiCall = { memberService.getMember(memberId) },
                onCompleteTokenRefresh = { null })

        val response =
            responseHandler.safeApiCall(
                apiCall = { memberService.getMember(memberId) },
                onCompleteTokenRefresh = { retryResponse })

        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }

    override suspend fun deleteMember(memberId: String): LoopyResult<Nothing?>? {
        val response =
            responseHandler.safeApiCall(
                apiCall = { memberService.deleteMember(memberId) },
                onCompleteTokenRefresh = { null })

        return when (response) {
            is LoopyApiResponse.Error -> LoopyResult(
                data = null,
                result = ResponseResult.ERROR.name,
                error = ErrorData(code = response.code, message = response.message, data = null)
            )

            is LoopyApiResponse.Success -> response.data
        }
    }

    override suspend fun saveMemberId(memberId: String) {
        tokenManager.saveMemberId(memberId)
    }

    override suspend fun deleteMemberId() {
        tokenManager.deleteMemberId()
    }
}
