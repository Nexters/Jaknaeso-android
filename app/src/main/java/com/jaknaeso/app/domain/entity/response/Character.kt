package com.jaknaeso.app.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val oridinalNumber: Int,
    val bundleId: Int
)
