package com.jaknaeso.app.data.datastoreImpl

import com.jaknaeso.app.data.authentication.ApiCallAdapter
import com.jaknaeso.app.data.datastore.MemberDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import com.jaknaeso.app.data.service.MemberService
import com.jaknaeso.app.data.token.TokenManager
import javax.inject.Inject

class MemberDatastoreImpl @Inject constructor(
    private val memberService: MemberService,
    private val apiCallAdapter: ApiCallAdapter,
    private val tokenManager: TokenManager
) : MemberDatastore {
    override suspend fun getMember(memberId: String): LoopyResult<MemberResponse>? {
        return apiCallAdapter.getApiResult { memberService.getMember(memberId) }
    }

    override suspend fun deleteMember(memberId: String): LoopyResult<Nothing?>? {
        return apiCallAdapter.getApiResult { memberService.deleteMember(memberId) }
    }

    override suspend fun saveMemberId(memberId: String) {
        tokenManager.saveMemberId(memberId)
    }

    override suspend fun deleteMemberId() {
        tokenManager.deleteMemberId()
    }
}
