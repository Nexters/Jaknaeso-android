package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.usecase.GetCharacterUseCase
import com.jaknaeso.app.domain.usecase.GetMemberSubmissionsResultUseCase
import com.jaknaeso.app.presentation.contract.ReportEffect
import com.jaknaeso.app.presentation.contract.ReportEvent
import com.jaknaeso.app.presentation.contract.ReportState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewmodel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getMemberSubmissionsResultUseCase: GetMemberSubmissionsResultUseCase
) :
    BaseViewModel<ReportEvent, ReportState, ReportEffect>() {

    override fun createInitialState(): ReportState {
        return ReportState(characterOrdinalWord = "", reportTitle = "첫번째 캐릭터")
    }

    override fun handleEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.GetInitialData -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getCharacters()
                    getSubmissionsResult(event.bundleId, currentState.reportTitle)
                }
            }

            is ReportEvent.SelectCharacterBundle -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getSubmissionsResult(event.bundleId, event.ordinalWord)
                }
            }

            ReportEvent.ClickHome -> setEffect(ReportEffect.NavigateToHome)
            ReportEvent.ClickProfile -> setEffect(ReportEffect.NavigateToProfile)
        }
    }

    private suspend fun getCharacters() {
        getCharacterUseCase().asResult().collect {
            when (it) {
                is Result.Error -> {
                    Log.e("ReportViewmodel", "${it.exception}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> setState { copy(characters = it.data) }
            }
        }
    }

    private suspend fun getSubmissionsResult(bundleId: String, ordinalWord: String) {
        getMemberSubmissionsResultUseCase(bundleId).asResult().collect {
            when (it) {
                is Result.Error -> {
                    Log.e("ReportViewmodel", "${it.exception}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> setState { copy(submissionsResult = it.data, reportTitle = ordinalWord) }
            }
        }
    }

}
