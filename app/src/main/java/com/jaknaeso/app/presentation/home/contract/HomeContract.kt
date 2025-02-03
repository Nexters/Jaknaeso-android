package com.jaknaeso.app.presentation.home.contract

import com.jaknaeso.app.domain.entity.Round
import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState
import kotlinx.collections.immutable.ImmutableList

sealed interface HomeEvent : UiEvent {
    data object ClickTodayQuestion
}

data class HomeState(
    val rounds: ImmutableList<Round>
) : UiState

sealed interface HomeEffect : UiEffect {
}
