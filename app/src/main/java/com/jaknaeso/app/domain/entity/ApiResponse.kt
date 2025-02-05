package com.jaknaeso.app.domain.entity

data class ApiResponse<T>(
    var result: String?,
    var data: T?,
    var error: Error?
)
