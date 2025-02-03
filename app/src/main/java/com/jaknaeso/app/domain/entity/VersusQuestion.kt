package com.jaknaeso.app.domain.entity

data class VersusQuestion(
    val roundIndex: Int,
    val question: String,
    val options: List<String>
)
