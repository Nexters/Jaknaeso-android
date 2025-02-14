package com.jaknaeso.app.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val accessToken: String
)
