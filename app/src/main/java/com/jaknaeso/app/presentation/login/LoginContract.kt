package com.jaknaeso.app.presentation.login

import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState

sealed interface LoginEvent : UiEvent {
    data object ClickKakaoLogin : LoginEvent
}

data object LoginState : UiState

sealed interface LoginEffect : UiEffect {
    data object NavigateToHome : LoginEffect
}

