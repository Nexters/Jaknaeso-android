package com.jaknaeso.app.data.model

data class Error(
    val code: ErrorCode,
    val messge: String,
    val data: Any?
)
