package com.jaknaeso.app.domain.entity

data class ErrorData(
    var code: Int,
    val message: String,
    val data: Any?
)
