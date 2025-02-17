package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailResponse(
    val characterNo: String,
    val name: String,
    val characterType: String,
    val description: String,
    val startDate: String,
    val endDate: String
)
