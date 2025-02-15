package com.jaknaeso.app.data.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingSubmissionsInfoRequest(
    val submissionsInfo: List<SurveyOptionSubmission>
)
