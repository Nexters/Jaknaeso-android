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
                if (response?.result == ResponseResult.ERROR.name) {
                    throw Exception(response.error?.message)
                } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                    throw Exception(response.result)
                } else {
                    val result = response?.data?.characters?.map {
                        Character(characterNo = it.characterNo, characterId = it.characterId, bundleId = it.bundleId)
                    } ?: emptyList()
                    emit(result)
                }
            } else {
                throw Exception("memberId를 찾을 수 없습니다.")
            }
        }
    }
}
