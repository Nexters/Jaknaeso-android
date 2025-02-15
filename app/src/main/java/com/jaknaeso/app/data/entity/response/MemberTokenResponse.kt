package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberTokenResponse(
    val memberId: Int,
    val isCompletedOnboarding: Boolean,
    val tokenInfo: TokenInfo
)
