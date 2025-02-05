package com.jaknaeso.app.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequest(
    val accessToken: String
)
