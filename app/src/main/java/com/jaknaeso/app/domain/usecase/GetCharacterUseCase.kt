package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.Character
import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Flow<List<Character>> {
        val memberId = memberRepository.getMemberId().first()
        return flow {
            if (memberId != null) {
                val response = characterRepository.getCharacters(memberId = memberId.toInt())
                if (response.result == ResponseResult.SUCCESS.name) {
                    val result = response.data?.characters?.map {
                        Character(ordinalWord = it.oridinalNumber.toKoreanOrdinal(), bundleId = it.bundleId)
                    } ?: emptyList()
                    emit(result)
                } else {
                    throw Exception(response.error?.message)
                }
            }
        }
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
                    tenPart == 0 -> "${unitText} 번째"
                    unitPart == 0 -> "${tenText} 번째"
                    else -> "${tenText}${unitText} 번째"
                }
            }
        }
    }

}
