package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.data.model.ApiResponse
import com.jaknaeso.app.data.model.request.TokenRequest
import com.jaknaeso.app.data.model.response.MemberTokenResponse

interface LoginRemoteDatastore {
    suspend fun getMemberToken(request: TokenRequest): ApiResponse<MemberTokenResponse>
}
