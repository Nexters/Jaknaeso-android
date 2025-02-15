package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyRecord(
    val question: String,
    val answer: String,
    val retrospective: String?,
    val submittedAt: String
)
