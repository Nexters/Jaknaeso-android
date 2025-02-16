package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.R
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.LatestCharacterBrief
import com.jaknaeso.app.domain.repository.CharacterRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLatestCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(): Flow<LatestCharacterBrief> {
        val memberId = memberRepository.getMemberId().first()
        if (memberId != null) {
            val response = characterRepository.getLatestCharacter(memberId)
            if (response?.result == ResponseResult.ERROR.name) {
                return flow { throw Exception(response.error?.message) }
            } else {
                return flow {
                    emit(
                        LatestCharacterBrief(
                            characterNo = response?.data?.characterNo ?: "",
                            characterType = response?.data?.characterType ?: "",
                            lottieRawFile = R.raw.balance //임시
                        )
                    )
                }
            }
        } else {
            return flow { throw Exception("memberId를 찾을 수 없습니다.") }
        }
    }

    fun mapCharacterTypeToLottieRawFile(characterType: String) {

    }
}
