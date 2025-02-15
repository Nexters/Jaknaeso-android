package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberService {
    @GET("/api/v1/members/{memberId}")
    suspend fun getMember(@Path("memberId") memberId: String): Response<LoopyResult<MemberResponse>>
}
