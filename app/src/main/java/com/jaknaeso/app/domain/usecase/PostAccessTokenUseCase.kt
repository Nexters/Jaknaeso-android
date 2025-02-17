package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.domain.model.TokenAndOnBoardingInfo
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostAccessTokenUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val memberRepository: MemberRepository
) {
    suspend operator fun invoke(token: String): Flow<TokenAndOnBoardingInfo> {
        val response = loginRepository.getMemberToken(TokenRequest(token))
        return flow {
            if (response?.result == ResponseResult.ERROR.name) {
                throw Exception(response.error?.message)
            } else if (response?.result == ResponseResult.REFRESH_FAILED.name) {
                throw Exception(response.result)
            } else {
                loginRepository.saveAccessToken(response?.data!!.tokenInfo.accessToken)
                loginRepository.saveRefreshToken(response.data!!.tokenInfo.refreshToken)
                memberRepository.saveMemberId(response.data!!.memberId.toString())
                emit(
                    TokenAndOnBoardingInfo(
                        accessToken = response.data!!.tokenInfo.accessToken,
                        refreshToken = response.data!!.tokenInfo.refreshToken,
                        isCompletedOnboarding = response.data!!.isCompletedOnboarding
                    )
                )
            }
        }
    }
}
