package com.jaknaeso.app.domain.model

data class Round(
    val submissionId: Int?,
    val index: Int,
    val state: QuestionState
)
