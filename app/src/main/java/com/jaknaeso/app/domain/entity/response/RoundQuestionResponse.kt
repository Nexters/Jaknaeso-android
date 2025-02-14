package com.jaknaeso.app.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class RoundQuestionResponse(
    val id: Int,
    val contents: String,
    val surveyType: String,
    val options: List<Options>
)
