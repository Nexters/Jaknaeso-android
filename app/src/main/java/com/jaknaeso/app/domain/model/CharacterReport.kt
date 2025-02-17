package com.jaknaeso.app.domain.model

data class CharacterReport(
    val characterId: Int?,
    val characterNo: String?,
    val name: String?,
    val lottieRawFile: Int?,
    val description: String?,
    val duration: String?, // "startDate-endDate" 형식
    val mainTraits: List<String>,
    val strengths: List<String>,
    val weaknesses: List<String>,
)
