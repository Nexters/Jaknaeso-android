package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class MemberResponse(
    val name: String,
    val email: String
)
