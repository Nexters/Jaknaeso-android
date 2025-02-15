package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    val accessToken: String,
    val refreshToken: String
)
