package com.jaknaeso.app.domain.entity.response

import kotlinx.serialization.Serializable

@Serializable
data class Options(
    val id: Int,
    val optionContents: String
)
