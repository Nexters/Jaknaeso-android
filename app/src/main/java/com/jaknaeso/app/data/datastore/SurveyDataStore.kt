package com.jaknaeso.app.data.datastore

import com.jaknaeso.app.domain.entity.LoopyResult
import com.jaknaeso.app.domain.entity.response.BundleRoundsResponse

interface SurveyDataStore {
    suspend fun getSurveysHistory(): LoopyResult<BundleRoundsResponse>
}
