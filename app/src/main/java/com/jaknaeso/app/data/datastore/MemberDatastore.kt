package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse

interface MemberDatastore {
    suspend fun getMember(memberId: String): LoopyResult<MemberResponse>?
    suspend fun deleteMember(memberId: String): LoopyResult<Nothing?>?
    suspend fun saveMemberId(memberId: String)
    suspend fun deleteMemberId()
}
