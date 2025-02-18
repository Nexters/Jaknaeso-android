package com.jaknaeso.app.domain.usecase

import javax.inject.Inject

class MapToKoreanOrdinalWordUseCase @Inject constructor() {
    operator fun invoke(number: Int): String {
        return number.toKoreanOrdinal()
    }

    fun Int.toKoreanOrdinal(): String {
        return when (this) {
            1 -> "첫번째 캐릭터"
            2 -> "두번째 캐릭터"
            3 -> "세번째 캐릭터"
            4 -> "네번째 캐릭터"
            5 -> "다섯번째 캐릭터"
            6 -> "여섯번째 캐릭터"
            7 -> "일곱번째 캐릭터"
            8 -> "여덟번째 캐릭터"
            9 -> "아홉번째 캐릭터"
            10 -> "열번째 캐릭터"
            else -> {
                val units = listOf("", "한", "두", "세", "네", "다섯", "여섯", "일곱", "여덟", "아홉")
                val tens = listOf("", "열", "스물", "서른", "마흔", "쉰", "예순", "일흔", "여든", "아흔")

                val tenPart = this / 10
                val unitPart = this % 10

                val tenText = tens[tenPart]
                val unitText = units[unitPart]

                when {
                    tenPart == 0 -> "${unitText}번째 캐릭터"
                    unitPart == 0 -> "${tenText}번째 캐릭터"
                    else -> "${tenText}${unitText}번째 캐릭터"
                }
            }
        }
    }
}
