package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse

interface MemberDatastore {
    suspend fun getMember(memberId: String): LoopyResult<MemberResponse>
}
