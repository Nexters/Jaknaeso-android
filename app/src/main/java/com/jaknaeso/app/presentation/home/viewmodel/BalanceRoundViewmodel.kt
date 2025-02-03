package com.jaknaeso.app.presentation.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.usecase.GetBalanceQuestionUseCase
import com.jaknaeso.app.presentation.common.BaseViewModel
import com.jaknaeso.app.presentation.home.contract.BalanceRoundEffect
import com.jaknaeso.app.presentation.home.contract.BalanceRoundEvent
import com.jaknaeso.app.presentation.home.contract.BalanceRoundState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceRoundViewmodel @Inject constructor(private val getBalanceQuestionUseCase: GetBalanceQuestionUseCase) :
    BaseViewModel<BalanceRoundEvent, BalanceRoundState, BalanceRoundEffect>() {
    override fun createInitialState(): BalanceRoundState {
        return BalanceRoundState()
    }

    override fun handleEvent(event: BalanceRoundEvent) {
        when (event) {
            is BalanceRoundEvent.GetBalanceQuestion -> {
                viewModelScope.launch {
                    val balanceQuestion = getBalanceQuestionUseCase(event.roundIndex.toInt())
                    setState { copy(balanceQuestion) }
                }
            }

            BalanceRoundEvent.SelectOption -> {

            }
        }
    }

}
