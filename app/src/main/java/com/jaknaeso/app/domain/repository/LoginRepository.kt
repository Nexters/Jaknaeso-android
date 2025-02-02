package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.model.ApiResponse
import com.jaknaeso.app.data.model.request.TokenRequest
import com.jaknaeso.app.data.model.response.MemberTokenResponse

interface LoginRepository {
    suspend fun getMemberToken(request: TokenRequest): ApiResponse<MemberTokenResponse>
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun deleteAccessToken(token: String)
    suspend fun deleteRefreshToken(token: String)
}
