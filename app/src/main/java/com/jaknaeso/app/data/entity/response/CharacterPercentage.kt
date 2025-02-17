package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPercentage(
    val keyword: String,
    val percentage: Float
)

