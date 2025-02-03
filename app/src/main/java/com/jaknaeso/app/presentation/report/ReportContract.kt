package com.jaknaeso.app.presentation.report

import com.jaknaeso.app.presentation.common.UiEffect
import com.jaknaeso.app.presentation.common.UiEvent
import com.jaknaeso.app.presentation.common.UiState


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
