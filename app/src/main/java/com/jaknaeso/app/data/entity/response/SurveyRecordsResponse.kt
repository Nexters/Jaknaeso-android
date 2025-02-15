package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class SurveyRecordsResponse(
    val surveyRecords: List<SurveyRecord>
)
