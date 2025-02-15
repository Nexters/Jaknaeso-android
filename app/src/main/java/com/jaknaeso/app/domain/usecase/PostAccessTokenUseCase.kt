package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.data.entity.request.TokenRequest
import com.jaknaeso.app.domain.model.TokenAndOnBoardingInfo
import com.jaknaeso.app.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostAccessTokenUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(token: String): Flow<TokenAndOnBoardingInfo> {
        val response = loginRepository.getMemberToken(TokenRequest(token))
        return flow {
            if (response.result == ResponseResult.SUCCESS.name && response.data != null) {
                emit(
                    TokenAndOnBoardingInfo(
                        accessToken = response.data!!.tokenInfo.accessToken,
                        refreshToken = response.data!!.tokenInfo.refreshToken,
                        isCompletedOnboarding = response.data!!.isCompletedOnboarding
                    )
                )
            } else {
                throw Exception(response.error?.message)
            }
        }
    }
}
