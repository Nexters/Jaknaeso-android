package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterGraphValueResponse(
    val valueReports: List<CharacterPercentage>
)
