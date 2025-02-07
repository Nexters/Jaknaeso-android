package com.jaknaeso.app.presentation.contract

sealed interface LoginEvent : UiEvent {
    data object ClickKakaoLogin : LoginEvent
}

data object LoginState : UiState

sealed interface LoginEffect : UiEffect {
    data object NavigateToHome : LoginEffect
}

