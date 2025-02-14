package com.jaknaeso.app.data.entity

data class ErrorData(
    var code: Int,
    val message: String,
    val data: Any?
)
