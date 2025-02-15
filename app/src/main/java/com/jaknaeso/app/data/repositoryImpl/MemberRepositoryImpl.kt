package com.jaknaeso.app.data.repositoryImpl

import com.jaknaeso.app.data.datastore.MemberDatastore
import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import com.jaknaeso.app.data.token.TokenManager
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDatastore: MemberDatastore,
    private val tokenManager: TokenManager
) : MemberRepository {
    override suspend fun getMember(memberId: String): LoopyResult<MemberResponse> {
        return memberDatastore.getMember(memberId)
    }

    override suspend fun getMemberId(): Flow<String?> {
        return tokenManager.getMemberId()
    }
}
