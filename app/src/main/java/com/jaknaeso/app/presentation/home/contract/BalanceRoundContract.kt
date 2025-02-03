package com.jaknaeso.app.presentation.home.contract

import com.jaknaeso.app.domain.entity.BalanceQuestion
import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState


sealed interface BalanceRoundEvent : UiEvent {
    data class GetBalanceQuestion(val roundIndex: String) : BalanceRoundEvent
    data class SelectOption(val roundIndex: String) : BalanceRoundEvent
}

data class BalanceRoundState(
    val balanceQuestion: BalanceQuestion? = null
) : UiState

sealed interface BalanceRoundEffect : UiEffect {
    data object NavigateToHome : BalanceRoundEffect
}
