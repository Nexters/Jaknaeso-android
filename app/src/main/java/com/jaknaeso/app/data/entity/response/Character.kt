package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val oridinalNumber: Int,
    val bundleId: Int
)
