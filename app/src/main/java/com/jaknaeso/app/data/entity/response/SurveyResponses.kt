package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyResponses(
    val surveyResponses: List<RoundQuestionResponse>
)
