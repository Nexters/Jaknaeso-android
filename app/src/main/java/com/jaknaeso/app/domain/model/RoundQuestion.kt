package com.jaknaeso.app.domain.model

data class RoundQuestion(
    val surveyId: String,
    val surveyType: SurveyType,
    val content: String,
    val options: List<Option>
)

