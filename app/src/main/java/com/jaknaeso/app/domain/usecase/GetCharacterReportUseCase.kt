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
            val userName = memberRepository.getMember(memberId)
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
                                keywordStrenthDescription = mapTwoMostStrengthToDescription(
                                    keywordGraphResponse.data?.valueReports,
                                    userName?.data?.name ?: "" //userName은 따로 에러처리 안 하고 바로 붙임
                                ),
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

    fun mapTwoMostStrengthToDescription(report: List<CharacterPercentage>?, userName: String): String {
        val arrangedKeyWordPercentage = report?.sortedBy { it.percentage }
        val keyword1 = arrangedKeyWordPercentage?.get(0)?.keyword
        val keyword2 = arrangedKeyWordPercentage?.get(2)?.keyword

        val firstStrength = mapToKoreanCharacterWord(keyword1)
        val secondStrength = mapToKoreanCharacterWord(keyword2)
        val conjunctiveParticle = getConjunctiveParticle(keyword1)
        val objectiveMarker = getObjectiveMarker(keyword2)
        return "${userName}님은 ${firstStrength}${conjunctiveParticle} ${secondStrength}${objectiveMarker}\n 가장 중요시 여기고 있어요."

    }

    fun mapToKoreanCharacterWord(word: String?): String {
        return when (word) {
            CharacterType.SELF_DIRECTION.name -> CharacterType.SELF_DIRECTION.koreanWord
            CharacterType.ADVENTURE.name -> CharacterType.ADVENTURE.koreanWord
            CharacterType.SECURITY.name -> CharacterType.SECURITY.koreanWord
            CharacterType.STABILITY.name -> CharacterType.STABILITY.koreanWord
            CharacterType.SUCCESS.name -> CharacterType.SUCCESS.koreanWord
            CharacterType.BENEVOLENCE.name -> CharacterType.BENEVOLENCE.koreanWord
            CharacterType.UNIVERSALISM.name -> CharacterType.UNIVERSALISM.koreanWord
            else -> ""
        }
    }

    fun getConjunctiveParticle(forwardWord: String?): String {
        if (forwardWord == null) return ""
        if (forwardWord in listOf(
                CharacterType.ADVENTURE.name,
                CharacterType.STABILITY.name,
                CharacterType.SELF_DIRECTION.name,
                CharacterType.UNIVERSALISM.name,
                CharacterType.SECURITY.name
            )
        ) {//모험, 안정, 자율,보편,안전
            return "과"
        } else {
            return "와"
        }
    }

    // "모험", "안정", "자율", "박애", "보편", "성취", "안전"순서로 삽입
    fun getObjectiveMarker(forwardWord: String?): String {
        if (forwardWord == null) return ""
        if (forwardWord in listOf(
                CharacterType.ADVENTURE.name,
                CharacterType.STABILITY.name,
                CharacterType.SELF_DIRECTION.name,
                CharacterType.UNIVERSALISM.name,
                CharacterType.SECURITY.name
            )
        ) {//모험, 안정, 자율,보편,안전
            return "과"
        } else {
            return "와"
        }
    }
}

