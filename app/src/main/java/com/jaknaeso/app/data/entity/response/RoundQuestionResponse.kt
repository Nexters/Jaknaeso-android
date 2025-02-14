package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class RoundQuestionResponse(
    val id: Int,
    val contents: String,
    val surveyType: String,
    val options: List<Options>
)
