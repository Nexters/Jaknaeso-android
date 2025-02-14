package com.jaknaeso.app.data.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class Options(
    val id: Int,
    val optionContents: String
)
