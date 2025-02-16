package com.jaknaeso.app.presentation.contract

sealed interface ProfileEvent : UiEvent {
    data object DeleteMember : ProfileEvent
    data object LogOutMember : ProfileEvent
    data object ClickPolicy : ProfileEvent
    data object ControlNotification : ProfileEvent
}

data object ProfileState : UiState
sealed interface ProfileEffect : UiEffect {
    data object NavigateToLogin : ProfileEffect
    data object NavigateToPolicy : ProfileEffect
}
