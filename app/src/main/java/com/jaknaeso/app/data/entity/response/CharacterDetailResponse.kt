package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailResponse(
    val characterId: Int,
    val characterNo: String,
    val characterType: String,
    val name: String,
    val description: String,
    val mainTraits: List<Trait>,
    val strengths: List<Trait>,
    val weaknesses: List<Trait>,
    val startDate: String,
    val endDate: String
)
