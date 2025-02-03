package com.jaknaeso.app.domain.entity

data class Round(
    val roundIndex: Int,
    val isLocked: Boolean,
    val isCompleted: Boolean,
    val isTodayQuestion: Boolean
)
