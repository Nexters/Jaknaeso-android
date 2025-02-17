package com.jaknaeso.app.data.service

import com.jaknaeso.app.data.entity.LoopyResult
import com.jaknaeso.app.data.entity.response.MemberResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberService {
    @GET("/api/v1/members/{memberId}")
    suspend fun getMember(@Path("memberId") memberId: String): ApiResponse<LoopyResult<MemberResponse>>

    @DELETE("/api/v1/members/{memberId}")
    suspend fun deleteMember(@Path("memberId") memberId: String): ApiResponse<LoopyResult<Nothing?>>
}
