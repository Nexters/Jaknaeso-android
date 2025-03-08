package com.jaknaeso.app.domain.usecase

import android.util.Log
import com.jaknaeso.app.domain.model.InitialRoute
import com.jaknaeso.app.domain.repository.LoginRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CheckLoginedUserUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(): InitialRoute {
        val result = coroutineScope {
            val accessTokenDeferred = async { loginRepository.getAccessToken() }
            val refreshTokenDeferred = async { loginRepository.getRefreshToken() }
            val isOnBoardingCompletedDeferred = async { loginRepository.getIsOnBoardingCompleted() }

            Triple(
                accessTokenDeferred.await(),
                refreshTokenDeferred.await(),
                isOnBoardingCompletedDeferred.await()
            )
        }

        val (accessToken, refreshToken, isOnBoardingCompleted) = result
        if (isOnBoardingCompleted == null) {
            return InitialRoute.Login
        }

        if (accessToken.isNullOrBlank() && refreshToken.isNullOrBlank() && !isOnBoardingCompleted) { //회원가입,온보딩 안됨
            return InitialRoute.Login
        } else if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank() && isOnBoardingCompleted) {//회원가입,온보딩 됨
            return InitialRoute.Home
        } else if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank() && !isOnBoardingCompleted) { //온보딩만 안됨
            Log.d("CheckLoginedUserUseCase", "isOnBoardingCompleted: ${isOnBoardingCompleted}")
            return InitialRoute.OnBoardingInformation
        } else { //온보딩만 된 경우는 존재하지 않음
            return InitialRoute.Login
        }
    }
}
