package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.data.entity.response.MemberTokenResponse

interface LoginRepository {
    suspend fun getMemberToken(request: TokenRequest): LoopyResult<MemberTokenResponse>?
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun getIsOnBoardingCompleted(): Boolean?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveIsOnBoardingCompleted(value: Boolean)
    suspend fun deleteAccessToken(token: String)
    suspend fun deleteRefreshToken(token: String)
    suspend fun deleteIsOnBoardingCompleted()
}
