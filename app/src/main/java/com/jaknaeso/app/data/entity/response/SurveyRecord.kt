package com.jaknaeso.app.data.entity.response

data class SurveyRecord(
    val question: String,
    val answer: String,
    val retrospective: String?,
    val submittedAt: String
)
