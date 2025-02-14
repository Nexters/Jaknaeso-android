package com.jaknaeso.app.data.entity

data class LoopyResult<T>(
    var result: String?,
    var data: T?,
    var error: ErrorData?
)
