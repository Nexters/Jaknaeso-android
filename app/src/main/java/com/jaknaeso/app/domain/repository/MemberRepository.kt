package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun getMember(memberId: String): LoopyResult<MemberResponse>
    suspend fun getMemberId(): Flow<String?>
}
