package com.jaknaeso.app.domain.entity

data class Error(
    var code: Int,
    val message: String,
    val data: Any?
)
