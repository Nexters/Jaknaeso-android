package com.jaknaeso.app.domain.repository

import com.jaknaeso.app.data.model.ApiResponse
import com.jaknaeso.app.data.model.response.MemberTokenResponse

interface LoginRepository {
    fun getMemberToken(token: String): ApiResponse<MemberTokenResponse>
}
