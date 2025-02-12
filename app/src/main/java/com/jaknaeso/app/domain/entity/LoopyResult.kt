package com.jaknaeso.app.domain.entity

data class LoopyResult<T>(
    var result: String?,
    var data: T?,
    var error: Error?
)
