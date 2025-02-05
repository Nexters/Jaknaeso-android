package com.jaknaeso.app.domain.model

data class Round(
    val roundIndex: Int,
    val isLocked: Boolean,
    val isCompleted: Boolean,
    val isTodayQuestion: Boolean
)
