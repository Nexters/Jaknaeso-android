package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.usecase.*
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
    private val getMemberSubmissionsResultUseCase: GetMemberSubmissionsResultUseCase,
    private val mapToKoreanOrdinalWord: MapToKoreanOrdinalWordUseCase,
    private val getLatestCharacterReportUseCase: GetLatestCharacterReportUseCase,
    private val getCharacterReportUseCase: GetCharacterReportUseCase,
) :
    BaseViewModel<ReportEvent, ReportState, ReportEffect>() {
    init {
        viewModelScope.launch(Dispatchers.IO) { getCharacters() }
    }

    override fun createInitialState(): ReportState {
        //모달 내용 초기화
        return ReportState(
            reportTitle = "첫번째 캐릭터", report = CharacterReport(
                null, null, null, null, null, null,
                emptyList(), emptyList(), emptyList()
            )
        )
    }

    override fun handleEvent(event: ReportEvent) {
        when (event) {
            ReportEvent.GetLatestData -> {
                initializeLatestAnswersHistory()
            }

            is ReportEvent.GetParticularBundle -> { //미션 라운드 아이템을 클릭해서 Report 페이지에 진입한 경우
                initializeParticularAnswersHistory(bundleId = event.bundleId, characterId = event.characterId)
            }

            is ReportEvent.SelectCharacterBundle -> { //캐릭터별 결과를 보는 경우
                viewModelScope.launch(Dispatchers.IO) {
                    getCharacterReportUseCase(bundleId = event.bundleId, characterId = event.characterId)
                    getSubmissionsResult(event.bundleId)
                    setState { copy(reportTitle = event.characterNo) }
                }
            }

            ReportEvent.ClickHome -> setEffect(ReportEffect.NavigateToHome)
            ReportEvent.ClickProfile -> setEffect(ReportEffect.NavigateToProfile)
        }
    }

    fun initializeLatestAnswersHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            //캐릭터 분석은 최신
            getLatestCharacterReportUseCase().asResult().collect { result ->
                when (result) {
                    is Result.Error -> {}
                    Result.Loading -> {}
                    is Result.Success -> {
                        setState { copy(report = result.data, reportTitle = "${result.data.characterNo} 캐릭터") }
                        val character = currentState.characters.find { it.characterNo == result.data.characterNo }
                        val bundleId = character?.bundleId.toString()
                        getSubmissionsResult(bundleId)
                    }
                }
            }
        }
    }

    private fun initializeParticularAnswersHistory(bundleId: String, characterId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getSubmissionsResult(bundleId)
            //특정 번들, 캐릭터 분석
            getCharacterReportUseCase(characterId = characterId, bundleId = bundleId)
                .asResult().collect {
                    when (it) {
                        is Result.Error -> {
                            if (it.exception.message == "404") {
                                setState { copy(isNoCharacterToShow = true) }
                            }
                        }

                        Result.Loading -> {}
                        is Result.Success -> {
                            setState { copy(report = it.data) }
                        }
                    }
                }
            //나의 답변 모아보기도 특정 번들 결과
            setState { copy(reportTitle = mapToKoreanOrdinalWord(bundleId.toInt())) }
        }
    }

    private suspend fun getCharacters() {
        getCharacterUseCase().asResult().collect {
            when (it) {
                is Result.Error -> {
                    if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                        setEffect(ReportEffect.NavigateToLogin)
                    }
                    Log.e("ReportViewmodel", "${it.exception}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> setState { copy(characters = it.data) }
            }
        }
    }

    private suspend fun getSubmissionsResult(bundleId: String) {
        getMemberSubmissionsResultUseCase(bundleId).asResult().collect {
            when (it) {
                is Result.Error -> {
                    if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                        setEffect(ReportEffect.NavigateToLogin)
                    }
                    Log.e("ReportViewmodel", "${it.exception}")
                    setState { copy(isLoading = false, isError = true) }
                }

                Result.Loading -> {}
                is Result.Success -> setState { copy(submissionsResult = it.data) }
            }
        }
    }

}
