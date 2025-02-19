package com.jaknaeso.app.presentation.contract

sealed interface MainEvent : UiEvent

data class MainState(
    val isInitialRoutingOngoing: Boolean = true,
    val initialRoute: String? = null
) : UiState

sealed interface MainEffect : UiEffect {
    data object NavigateToLogin : MainEffect
}
