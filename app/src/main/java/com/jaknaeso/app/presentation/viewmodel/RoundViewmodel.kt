package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.model.SurveyType
import com.jaknaeso.app.domain.usecase.GetBalanceQuestionUseCase
import com.jaknaeso.app.domain.usecase.UpdateRoundsStateUseCase
import com.jaknaeso.app.presentation.contract.RoundEffect
import com.jaknaeso.app.presentation.contract.RoundEvent
import com.jaknaeso.app.presentation.contract.RoundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoundViewmodel @Inject constructor(
    private val getBalanceQuestionUseCase: GetBalanceQuestionUseCase,
    private val updateRoundsStateUseCase: UpdateRoundsStateUseCase
) :
    BaseViewModel<RoundEvent, RoundState, RoundEffect>() {

    override fun createInitialState(): RoundState {
        return RoundState()
    }

    override fun handleEvent(event: RoundEvent) {
        viewModelScope.launch {
            when (event) {
                is RoundEvent.GetQuestion -> {
                    getRoundQuestion(event.bundleIndex)
                }

                is RoundEvent.SelectOption -> {
                    setState { copy(selectedOptionId = event.optionId) }
                }

                is RoundEvent.SaveWord -> setState { copy(enteredComment = event.comment) }

                is RoundEvent.ClickSubmitWordButton -> {
                    updateRoundsStateUseCase(
                        surveyId = currentState.surveyId!!,
                        optionId = currentState.selectedOptionId.toString(),
                        comment = currentState.enteredComment
                    )
                    setEffect(RoundEffect.NavigateToBalanceRoundComplete)
                }

                is RoundEvent.ClickPassEnterReason -> {
                    setEffect(RoundEffect.NavigateToBalanceRoundComplete)
                }

                RoundEvent.ClickBackButton -> setEffect(RoundEffect.NavigateToBack)
                RoundEvent.OpenModal -> setEffect(RoundEffect.OpenModal)
                RoundEvent.CloseModal -> setEffect(RoundEffect.CloseModal)
            }
        }
    }

    suspend fun getRoundQuestion(bundleIndex: String) {
        getBalanceQuestionUseCase(bundleIndex).collectLatest {
            val isBalanceRound = if (it?.surveyType == SurveyType.BALANCE) true else false
            setState { copy(isLoading = false, question = it, isBalanceRound = isBalanceRound, surveyId = it?.id) }
        }
    }
}
