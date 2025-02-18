package com.jaknaeso.app.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.domain.usecase.PostAccessTokenUseCase
import com.jaknaeso.app.presentation.contract.LoginEffect
import com.jaknaeso.app.presentation.contract.LoginEvent
import com.jaknaeso.app.presentation.contract.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val application: Application,
    private val loginRepository: LoginRepository,
    private val postAccessTokenUseCase: PostAccessTokenUseCase,
) :
    BaseViewModel<LoginEvent, LoginState, LoginEffect>() {
    override fun createInitialState(): LoginState {
        return LoginState
    }

    override fun handleEvent(event: LoginEvent) {
    }

    fun postAccessToken(token: String) {
        viewModelScope.launch {
            postAccessTokenUseCase(token).asResult().collect {
                when (it) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        Log.d(
                            "LoginViewmodel",
                            "accessToken:${it.data.accessToken}, refreshToken:${it.data.refreshToken}, isCompletedOnboarding:${it.data.isCompletedOnboarding}"
                        )
                        navigateNextScreen(it.data.isCompletedOnboarding)
                    }
                }
            }
        }
    }

    fun navigateNextScreen(isOnboardingCompleted: Boolean) {
        if (isOnboardingCompleted) {
            setEffect(LoginEffect.NavigateToHome)
        } else {
            setEffect(LoginEffect.NavigateToOnboarding)
        }
    }
}
