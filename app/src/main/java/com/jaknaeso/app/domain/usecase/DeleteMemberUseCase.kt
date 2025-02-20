package com.jaknaeso.app.domain.usecase

import android.util.Log
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.token.TokenManager
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteMemberUseCase @Inject constructor(
    private val memberRepository: MemberRepository,
    private val tokenManager: TokenManager,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Flow<Nothing?> {
        loginRepository.deleteIsOnBoardingCompleted()
        val memberId = tokenManager.getMemberId().firstOrNull()
        if (memberId != null) {
            val response = memberRepository.deleteMember(memberId)
            Log.d("DeleteMemberUseCase", "response: ${response}")
            if (response?.result == ResponseResult.SUCCESS.name) {
                tokenManager.deleteMemberId()
                tokenManager.deleteAccessToken()
                tokenManager.deleteRefreshToken()
                return flow { emit(response.data) }
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                return flow { throw Exception(response.result) }
            } else {
                return flow { throw Exception(response?.error?.message) }
            }
        } else {
            return flow { throw Exception("memberId 값이 비었습니다.") }
        }
    }
}
