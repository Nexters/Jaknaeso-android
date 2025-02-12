package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.BalanceQuestion


sealed interface BalanceRoundEvent : UiEvent {
    data class GetBalanceQuestion(val roundIndex: String) : BalanceRoundEvent
    data class SelectOption(val roundIndex: String) : BalanceRoundEvent
    data object ClickSubmitReasonButton : BalanceRoundEvent
    data object ClickPassEnterReason : BalanceRoundEvent
    data object ClickBackButton : BalanceRoundEvent
}

data class BalanceRoundState(
    val balanceQuestion: BalanceQuestion? = null
) : UiState

sealed interface BalanceRoundEffect : UiEffect {
    data object NavigateToBack : BalanceRoundEffect
    data object NavigateToBalanceRoundComplete : BalanceRoundEffect
    data object OpenModal : BalanceRoundEffect
}
