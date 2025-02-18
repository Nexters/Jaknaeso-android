package com.jaknaeso.app.data.entity

data class ErrorData(
    var code: String,
    val message: String,
    val data: Any?
)
