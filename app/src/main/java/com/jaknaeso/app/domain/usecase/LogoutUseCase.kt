package com.jaknaeso.app.domain.usecase

import com.jaknaeso.app.data.token.TokenManager
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val tokenManager: TokenManager) {
    suspend operator fun invoke() {
        tokenManager.deleteMemberId()
        tokenManager.deleteAccessToken()
        tokenManager.deleteRefreshToken()
    }
}
