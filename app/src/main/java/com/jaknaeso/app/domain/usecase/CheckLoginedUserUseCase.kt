package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.model.InitialRoute
import com.jaknaeso.app.domain.repository.LoginRepository
import javax.inject.Inject

class CheckLoginedUserUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): InitialRoute {
        val accessToken = loginRepository.getAccessToken()
        val refreshToken = loginRepository.getRefreshToken()
        val isOnBoardingCompleted = loginRepository.getIsOnBoardingCompleted() ?: false
        if (accessToken.isNullOrBlank() || refreshToken.isNullOrBlank()) { //회원가입이 안됨
            return InitialRoute.Login
        } else if (isOnBoardingCompleted) {
            return InitialRoute.Home
        } else { //회원가입은 했는데 온보딩 제출이 안됨
            return InitialRoute.OnBoardingInformation
        }
    }
}
