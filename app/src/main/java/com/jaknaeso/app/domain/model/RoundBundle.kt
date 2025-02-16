package com.jaknaeso.app.domain.model

data class RoundBundle(
    val bundleId: Int?,
    val wholeRounds: List<Round>,
    val faceRounds: List<Round>,
    val isTodayRoundCompleted: Boolean,
    val remainRound: Int,
)
