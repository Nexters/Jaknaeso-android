package com.jaknaeso.app.domain.model

data class RoundResult(
    val index: Int,
    val question: String,
    val answer: String,
    val word: String,
    val submittedAt: String
)
