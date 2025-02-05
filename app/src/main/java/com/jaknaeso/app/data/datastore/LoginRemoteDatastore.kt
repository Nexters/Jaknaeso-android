package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.domain.entity.ApiResponse
import com.jaknaeso.app.domain.entity.request.TokenRequest
import com.jaknaeso.app.domain.entity.response.MemberTokenResponse

interface LoginRemoteDatastore {
    suspend fun getMemberToken(request: TokenRequest): ApiResponse<MemberTokenResponse>
}
