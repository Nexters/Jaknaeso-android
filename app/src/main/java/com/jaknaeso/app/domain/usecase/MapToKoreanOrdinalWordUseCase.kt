package com.jaknaeso.app.domain.usecase

import javax.inject.Inject

class MapToKoreanOrdinalWordUseCase @Inject constructor() {
    operator fun invoke(number: Int): String {
        return number.toKoreanOrdinal()
    }

    fun Int.toKoreanOrdinal(): String {
        return when (this) {
            1 -> "첫"
            2 -> "두"
            3 -> "세"
            4 -> "네"
            5 -> "다섯"
            6 -> "여섯"
            7 -> "일곱"
            8 -> "여덟"
            9 -> "아홉"
            10 -> "열"
            else -> {
                val units = listOf("", "한", "두", "세", "네", "다섯", "여섯", "일곱", "여덟", "아홉")
                val tens = listOf("", "열", "스물", "서른", "마흔", "쉰", "예순", "일흔", "여든", "아흔")

                val tenPart = this / 10
                val unitPart = this % 10

                val tenText = tens[tenPart]
                val unitText = units[unitPart]

                when {
                    tenPart == 0 -> "${unitText}"
                    unitPart == 0 -> "${tenText}"
                    else -> "${tenText}${unitText}"
                }
            }
        }
    }
}
