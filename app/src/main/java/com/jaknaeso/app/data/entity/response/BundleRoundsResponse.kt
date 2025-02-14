package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class BundleRoundsResponse(
    val bundleId: Int?,
    val surveyHistoryDetails: List<SubmissionId>?,
    val nextSurveyIndex: Int?,
    val isCompleted: Boolean,
)
