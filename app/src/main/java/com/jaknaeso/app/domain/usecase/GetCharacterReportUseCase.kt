package com.jaknaeso.app.domain.usecase

import android.util.Log
import com.jaknaeso.app.R
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.model.CharacterType
import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterReportUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val memberRepository: MemberRepository,
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(characterId: String, bundleId: String): Flow<CharacterReport> {
        val memberId = memberRepository.getMemberId().firstOrNull()
        if (memberId != null) {
            val response = characterRepository.getCharacterReport(characterId, memberId)
            Log.e("ReportViewmodel", "getCharacterReport: ${response}")
            if (response?.result == ResponseResult.ERROR.name) {
                return flow { throw Exception(response.error?.code.toString()) }
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                return flow { throw Exception(response.result) }
            } else {
                return flow {}
            }
            //            else {
//                return flow {
//                    val data = response?.data
//                    emit(
//                        CharacterReport(
//                            characterId = data?.characterId!!,
//                            characterNo = data.characterNo,
//                            name = data.name,
//                            lottieRawFile = mapCharacterTypeToLottieRawFile(data.characterType),
//                            description = data.description,
//                            duration = "${data.startDate} - ${data.endDate}",
//                            mainTraits = data.mainTraits.map { it.description },
//                            strengths = data.strengths.map { it.description },
//                            weaknesses = data.weaknesses.map { it.description },
//
//                            )
//                    )
//                }
//            }
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
}

