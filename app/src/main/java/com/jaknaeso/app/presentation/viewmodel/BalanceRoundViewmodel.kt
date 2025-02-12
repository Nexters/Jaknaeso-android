package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.usecase.GetBalanceQuestionUseCase
import com.jaknaeso.app.domain.usecase.UpdateRoundsStateUseCase
import com.jaknaeso.app.presentation.contract.BalanceRoundEffect
import com.jaknaeso.app.presentation.contract.BalanceRoundEvent
import com.jaknaeso.app.presentation.contract.BalanceRoundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceRoundViewmodel @Inject constructor(
    private val getBalanceQuestionUseCase: GetBalanceQuestionUseCase,
    private val updateRoundsStateUseCase: UpdateRoundsStateUseCase
) :
    BaseViewModel<BalanceRoundEvent, BalanceRoundState, BalanceRoundEffect>() {
    override fun createInitialState(): BalanceRoundState {
        return BalanceRoundState()
    }

    override fun handleEvent(event: BalanceRoundEvent) {
        viewModelScope.launch {
            when (event) {
                is BalanceRoundEvent.GetBalanceQuestion -> {
                    val balanceQuestion = getBalanceQuestionUseCase(event.roundIndex.toInt())
                    setState { copy(balanceQuestion) }
                }

                is BalanceRoundEvent.SelectOption -> {
                    updateRoundsStateUseCase(event.roundIndex.toInt())
                    setEffect(BalanceRoundEffect.OpenModal)
                }

                is BalanceRoundEvent.ClickSubmitReasonButton -> setEffect(BalanceRoundEffect.NavigateToBalanceRoundComplete)

                BalanceRoundEvent.ClickPassEnterReason -> setEffect(BalanceRoundEffect.NavigateToBalanceRoundComplete)
                BalanceRoundEvent.ClickBackButton -> setEffect(BalanceRoundEffect.NavigateToBack)
            }
        }
    }

}
