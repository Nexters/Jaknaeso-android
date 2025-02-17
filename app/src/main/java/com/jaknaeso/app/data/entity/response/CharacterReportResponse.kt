package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterReportResponse(
    val valueReports: List<CharacterPercentage>
)
