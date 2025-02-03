package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.usecase.JudgeAllRoundCompletedUseCase
import com.jaknaeso.app.presentation.common.BaseViewModel
import com.jaknaeso.app.presentation.contract.ReportEffect
import com.jaknaeso.app.presentation.contract.ReportEvent
import com.jaknaeso.app.presentation.contract.ReportState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewmodel @Inject constructor(private val judgeAllRoundCompletedUseCase: JudgeAllRoundCompletedUseCase) :
    BaseViewModel<ReportEvent, ReportState, ReportEffect>() {

    init {
        viewModelScope.launch {
            val isReportExisted = judgeAllRoundCompletedUseCase()
            setState { copy(isReportExisted = isReportExisted) }
        }
    }

    override fun createInitialState(): ReportState {
        return ReportState()
    }

    override fun handleEvent(event: ReportEvent) {
        when (event) {
            ReportEvent.ClickHome -> setEffect(ReportEffect.NavigateToHome)
            ReportEvent.ClickProfile -> setEffect(ReportEffect.NavigateToProfile)
        }
    }

}
