package com.jaknaeso.app.presentation.contract

sealed interface LoginEvent : UiEvent
data object LoginState : UiState

sealed interface LoginEffect : UiEffect {
    data object NavigateToHome : LoginEffect
    data object NavigateToOnboarding : LoginEffect
}

