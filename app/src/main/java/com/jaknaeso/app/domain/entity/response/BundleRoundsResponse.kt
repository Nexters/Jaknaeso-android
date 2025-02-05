package com.jaknaeso.app.domain.entity.response

data class BundleRoundsResponse(
    val bundleId: Int?,
    val surveyHistoryDetails: List<SubmissionId>?,
    val nextSurveyIndex: Int?
)
