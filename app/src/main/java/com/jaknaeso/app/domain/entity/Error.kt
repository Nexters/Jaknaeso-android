package com.jaknaeso.app.domain.entity

data class Error(
    val code: ErrorCode,
    val message: String,
    val data: Any?
)
