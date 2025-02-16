package com.jaknaeso.app.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class LoopyResult<T>(
    var result: String?,
    var data: T?,
    var error: ErrorData?
)
