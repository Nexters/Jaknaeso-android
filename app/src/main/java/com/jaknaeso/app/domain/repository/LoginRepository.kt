package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.request.TokenRequest
import com.jaknaeso.app.domain.entity.response.MemberTokenResponse

interface LoginRepository {
    suspend fun getMemberToken(request: TokenRequest): LoopyApiResponse<LoopyResult<MemberTokenResponse>>
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun deleteAccessToken(token: String)
    suspend fun deleteRefreshToken(token: String)
}
