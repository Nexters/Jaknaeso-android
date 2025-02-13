package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.Character


sealed interface ReportEvent : UiEvent {
    data object ClickHome : ReportEvent
    data object ClickProfile : ReportEvent
}

data class ReportState(
    val isReportExisted: Boolean = false,
    val chracterOrdinalWord: String,
    val characters: List<Character> = emptyList(),
) : UiState

sealed interface ReportEffect : UiEffect {
    data object NavigateToHome : ReportEffect
    data object NavigateToProfile : ReportEffect
}
