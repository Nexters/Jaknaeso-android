package com.jaknaeso.app.presentation.contract


sealed interface ReportEvent : UiEvent {
    data object ClickHome : ReportEvent
    data object ClickProfile : ReportEvent
}

data class ReportState(
    val isReportExisted: Boolean = false
) : UiState

sealed interface ReportEffect : UiEffect {
    data object NavigateToHome : ReportEffect
    data object NavigateToProfile : ReportEffect
}
