package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.data.entity.response.MemberTokenResponse

interface LoginRemoteDatastore {
    suspend fun getMemberToken(request: TokenRequest): LoopyResult<MemberTokenResponse>?
}
