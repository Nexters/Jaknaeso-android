package com.jaknaeso.app.presentation.contract

import com.jaknaeso.app.domain.model.Character
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.model.RoundResult


sealed interface ReportEvent : UiEvent {
    data class GetCharactersList(val bundleId: String) : ReportEvent
    data object GetFirstCharacterData : ReportEvent
    data class GetParticularCharacterData(val bundleId: String) : ReportEvent
    data class SelectCharacterData(val characterNo: String, val characterId: String, val bundleId: String) :
        ReportEvent

    data object ClickHome : ReportEvent
    data object ClickProfile : ReportEvent
}

data class ReportState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isNoCharacterToShow: Boolean = false,
    val reportTitle: String,
    val characters: List<Character>? = emptyList(),
    val report: CharacterReport,
    val submissionsResult: List<RoundResult>? = emptyList(),
) : UiState

sealed interface ReportEffect : UiEffect {
    data object NavigateToLogin : ReportEffect
    data object NavigateToHome : ReportEffect
    data object NavigateToProfile : ReportEffect
    data object CompletedLoadCharacterList : ReportEffect
}
