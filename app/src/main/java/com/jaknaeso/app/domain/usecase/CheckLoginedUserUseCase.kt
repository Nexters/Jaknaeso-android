package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.model.InitialRoute
import com.jaknaeso.app.domain.repository.LoginRepository
import javax.inject.Inject

class CheckLoginedUserUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): InitialRoute {
        val accessToken = loginRepository.getAccessToken()
        val refreshToken = loginRepository.getRefreshToken()
        val isOnBoardingCompleted = loginRepository.getIsOnBoardingCompleted() ?: false
        if (accessToken.isNullOrBlank() && refreshToken.isNullOrBlank() && !isOnBoardingCompleted) { //회원가입,온보딩 안됨
            return InitialRoute.Login
        } else if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank() && isOnBoardingCompleted) {//회원가입,온보딩 됨
            return InitialRoute.Home
        } else if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank() && !isOnBoardingCompleted) { //온보딩만 안됨
            return InitialRoute.OnBoardingInformation
        } else { //온보딩만 된 경우는 존재하지 않음
            return InitialRoute.Login
        }
    }
}
