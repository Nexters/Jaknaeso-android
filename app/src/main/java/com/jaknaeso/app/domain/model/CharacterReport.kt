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
    val keywordPercentage: List<Float>,// "성장", "평화", "사회", "안전", "인정", "성취", "개인", "자유" 순서로 삽입
)
