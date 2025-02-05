package com.jaknaeso.app.domain.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val accessToken: String
)
