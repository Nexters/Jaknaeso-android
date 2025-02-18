package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class Character(
    val ordinalNumber: Int,
    val characterNo: String,
    val characterId: Int,
    val bundleId: Int,
    val isCompleted: Boolean
)
