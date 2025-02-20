package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.R
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.model.CharacterType
import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLatestCharacterReportUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Flow<CharacterReport> {
        val memberId = memberRepository.getMemberId().first()
        if (memberId != null) {
            val response = characterRepository.getLatestCharacter(memberId)
            if (response?.result == ResponseResult.ERROR.name) {
                return flow { throw Exception(response.error?.message) }
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                return flow { throw Exception(response.result) }
            } else {
                return flow {
                    val data = response?.data
                    emit(
                        CharacterReport(
                            characterId = data?.characterId!!,
                            characterNo = data.characterNo,
                            name = data.name,
                            lottieRawFile = mapCharacterTypeToLottieRawFile(data.characterType),
                            description = data.description,
                            duration = "${data.startDate} - ${data.endDate}",
                            mainTraits = data.mainTraits.map { it.description },
                            strengths = data.strengths.map { it.description },
                            weaknesses = data.weaknesses.map { it.description },
                            keywordStrenthDescription = "",
                            keywordPercentage = emptyList()
                        )
                    )
                }
            }
        } else {
            return flow { throw Exception("memberId를 찾을 수 없습니다.") }
        }
    }

    fun mapCharacterTypeToLottieRawFile(characterType: String): Int {
        return when (characterType) {
            CharacterType.SUCCESS.name -> R.raw.success
            CharacterType.SELF_DIRECTION.name -> R.raw.self_direction
            CharacterType.SECURITY.name -> R.raw.security2
            CharacterType.ADVENTURE.name -> R.raw.adventure
            CharacterType.STABILITY.name -> R.raw.stability
            CharacterType.BENEVOLENCE.name -> R.raw.benevolence
            CharacterType.UNIVERSALISM.name -> R.raw.universalism
            else -> R.raw.warning
        }
    }
}
