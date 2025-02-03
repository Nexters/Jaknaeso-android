package com.jaknaeso.app.presentation.home.contract

import com.jaknaeso.app.domain.entity.BalanceQuestion
import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState


sealed interface OptionsRoundEvent : UiEvent {
    data class GetBalanceQuestion(val roundIndex: String) : OptionsRoundEvent
    data object SelectOption : OptionsRoundEvent
}

data class OptionsRoundState(
    val balanceQuestion: BalanceQuestion? = null
) : UiState

sealed interface OptionsRoundEffect : UiEffect {
    data object NavigateToHome : OptionsRoundEffect
}
