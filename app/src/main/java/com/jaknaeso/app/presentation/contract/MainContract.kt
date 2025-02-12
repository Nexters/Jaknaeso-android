package com.jaknaeso.app.presentation.contract

sealed interface MainEvent : UiEvent

data object MainState : UiState

sealed interface MainEffect : UiEffect {
    data object NavigateToLogin : MainEffect
}
