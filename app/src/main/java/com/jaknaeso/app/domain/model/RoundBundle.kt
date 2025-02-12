package com.jaknaeso.app.domain.model

data class RoundBundle(
    val bundleId: Int?,
    val rounds: List<Round>,
    val isTodayRoundCompleted: Boolean
)
