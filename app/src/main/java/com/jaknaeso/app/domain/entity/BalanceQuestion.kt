package com.jaknaeso.app.domain.entity

data class BalanceQuestion(
    val id: Int,
    val roundIndex: Int,
    val question: String,
    val options: List<String>
)
