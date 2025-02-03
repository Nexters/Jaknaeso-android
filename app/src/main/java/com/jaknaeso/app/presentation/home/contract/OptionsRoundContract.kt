package com.jaknaeso.app.presentation.home.contract

import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState


sealed interface RoundEvent : UiEvent {
    data object SelectOption : RoundEvent
}

data object RoundState : UiState

sealed interface RoundEffect : UiEffect {
    data object NavigateToHome : RoundEffect
}
