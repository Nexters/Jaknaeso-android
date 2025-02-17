package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import com.jaknaeso.app.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharacterReportUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val memberRepository: MemberRepository,
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(characterId: String, bundleId: String) {
        val memberId = memberRepository.getMemberId().firstOrNull()
        if (memberId != null) {
            val response = characterRepository.getCharacterReport(characterId, memberId) //캐릭터 분석 정보
//            if (response?.result == ResponseResult.ERROR.name) {
//                return flow { throw Exception(response.error?.message) }
//            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
//                return flow { throw Exception(response.result) }
//            } else {
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
//        } else {
//            return flow { throw Exception("memberId를 찾을 수 없습니다.") }
//        }
        }
    }}
