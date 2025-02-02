package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.domain.repository.LoginRepository
import javax.inject.Inject

class CheckLoginedUserUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun isLoginedUser(): Boolean {
        val accessToken = loginRepository.getAccessToken()
        val refreshToken = loginRepository.getRefreshToken()
        if (accessToken.isNullOrBlank() && refreshToken.isNullOrBlank()) {
            return true
        }
        return false
    }
}
