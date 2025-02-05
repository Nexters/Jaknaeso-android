package com.jaknaeso.app.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberTokenResponse(
    val memberId: Int,
    val accessToken: String,
    val refreshToken: String
)
