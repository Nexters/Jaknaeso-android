package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.Character
import com.jaknaeso.app.domain.model.RoundResult


sealed interface ReportEvent : UiEvent {
    data class GetInitialData(val bundleId: String) : ReportEvent
    data class SelectCharacterBundle(val bundleId: String, val ordinalWord: String) : ReportEvent
    data object ClickHome : ReportEvent
    data object ClickProfile : ReportEvent
}

data class ReportState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val characterOrdinalWord: String,
    val characters: List<Character> = emptyList(),
    val submissionsResult: List<RoundResult> = emptyList(),
    val reportTitle: String
) : UiState

sealed interface ReportEffect : UiEffect {
    data object NavigateToHome : ReportEffect
    data object NavigateToProfile : ReportEffect
}
