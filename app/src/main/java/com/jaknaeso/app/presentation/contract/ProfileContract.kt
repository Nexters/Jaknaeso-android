package com.jaknaeso.app.presentation.contract

import dagger.multibindings.StringKey

sealed interface ProfileEvent : UiEvent {
    data object GetMemberInfo:ProfileEvent
    data object DeleteMember : ProfileEvent
    data object LogOutMember : ProfileEvent
    data object ClickPolicy : ProfileEvent
    data object ControlNotification : ProfileEvent
}

data class ProfileState(
    val name:String,
    val email:String
) : UiState

sealed interface ProfileEffect : UiEffect {
    data object NavigateToLogin : ProfileEffect
    data object NavigateToPolicy : ProfileEffect
}
