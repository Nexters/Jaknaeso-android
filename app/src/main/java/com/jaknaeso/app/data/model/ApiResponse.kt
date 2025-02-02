package com.jaknaeso.app.data.model

data class ApiResponse<T>(
    var result: String?,
    var data: T?,
    var error: Error?
)
