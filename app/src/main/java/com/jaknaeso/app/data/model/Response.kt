package com.jaknaeso.app.data.model

data class Response<T>(
    val result: ResponseResult,
    val data: T?,
    val error: Error?
)
