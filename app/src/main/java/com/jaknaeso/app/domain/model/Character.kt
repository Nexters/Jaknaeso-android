package com.jaknaeso.app.domain.model

data class Character(
    val ordinalNumber: Int,
    val characterNo: String,
    val characterId: Int,
    val bundleId: Int,
    val isCompleted: Boolean
)
