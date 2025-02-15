package com.jaknaeso.app.domain.model

data class TokenAndOnBoardingInfo(
    val accessToken: String,
    val refreshToken: String,
    val isCompletedOnboarding: Boolean
)
