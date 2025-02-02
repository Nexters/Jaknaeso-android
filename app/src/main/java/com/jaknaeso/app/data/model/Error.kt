package com.jaknaeso.app.data.model

data class Error(
    val code: ErrorCode,
    val message: String,
    val data: Any?
)
