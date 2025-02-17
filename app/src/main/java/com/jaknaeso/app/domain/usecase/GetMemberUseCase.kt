package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.model.MemberInfo
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMemberUseCase @Inject constructor(private val memberRepository: MemberRepository) {
    suspend operator fun invoke(): Flow<MemberInfo> {
        val memberId = memberRepository.getMemberId().firstOrNull()
        if (memberId != null) {
            val response = memberRepository.getMember(memberId)
            if (response?.result == ResponseResult.ERROR.name) {
                return flow { throw Exception(response.error?.message) }
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                return flow { throw Exception(response.result) }
            } else {
                return flow {
                    emit(
                        MemberInfo(
                            name = response?.data?.name ?: "이름 정보없음",
                            email = response?.data?.email ?: "이메일 정보없음"
                        )
                    )
                }
            }
        } else {
            return flow { throw Exception("memberId를 찾을 수 없습니다.") }
        }
    }
}
