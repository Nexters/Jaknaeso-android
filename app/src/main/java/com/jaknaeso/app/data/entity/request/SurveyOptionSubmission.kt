package com.jaknaeso.app.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class SurveyOptionSubmission(
    val surveyId: String,
    val optionId: String
)
