package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.R
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.response.CharacterPercentage
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.model.CharacterType
import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.round

class GetCharacterReportUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val memberRepository: MemberRepository,
) {
    suspend operator fun invoke(characterId: String): Flow<CharacterReport> {
        val memberId = memberRepository.getMemberId().firstOrNull()
        if (memberId != null) {
            val response = characterRepository.getCharacterReport(characterId, memberId)
            val keywordGraphResponse =
                characterRepository.getCharacterGraphValue(characterId = characterId, memberId = memberId)
            if (response?.result == ResponseResult.ERROR.name) {
                return flow { throw Exception(response.error?.code.toString()) }
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                return flow { throw Exception(response.result) }
            } else {
                if (keywordGraphResponse?.result == ResponseResult.SUCCESS.name) {
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
                                keywordPercentage = mapToKeywordPercentage(keywordGraphResponse.data?.valueReports)
                            )
                        )
                    }
                } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                    return flow { throw Exception(response.result) }
                } else {
                    return flow { throw Exception(response?.error?.code.toString()) }
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
            CharacterType.SECURITY.name -> R.raw.security
            CharacterType.ADVENTURE.name -> R.raw.adventure
            CharacterType.STABILITY.name -> R.raw.stability
            CharacterType.BENEVOLENCE.name -> R.raw.benevolence
            CharacterType.UNIVERSALISM.name -> R.raw.universalism
            else -> R.raw.warning
        }
    }

    fun mapToKeywordPercentage(report: List<CharacterPercentage>?): List<Float> {
        //"모험","안정","자율","박애","보편","성취","안전"
        val tmp = listOf(
            report?.find { it.keyword == CharacterType.ADVENTURE.name }?.percentage ?: 0f,
            report?.find { it.keyword == CharacterType.STABILITY.name }?.percentage ?: 0f,
            report?.find { it.keyword == CharacterType.SELF_DIRECTION.name }?.percentage ?: 0f,
            report?.find { it.keyword == CharacterType.BENEVOLENCE.name }?.percentage ?: 0f,
            report?.find { it.keyword == CharacterType.UNIVERSALISM.name }?.percentage ?: 0f,
            report?.find { it.keyword == CharacterType.SUCCESS.name }?.percentage ?: 0f,
            report?.find { it.keyword == CharacterType.SECURITY.name }?.percentage ?: 0f
        )
        return tmp.map {
            round((it / 100f) * 10f) / 10f
        }
    }
}

