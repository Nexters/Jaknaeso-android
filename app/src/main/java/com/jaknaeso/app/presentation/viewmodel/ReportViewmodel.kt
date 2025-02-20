package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.model.CharacterReport
import com.jaknaeso.app.domain.usecase.GetCharacterReportUseCase
import com.jaknaeso.app.domain.usecase.GetCharacterUseCase
import com.jaknaeso.app.domain.usecase.GetMemberSubmissionsResultUseCase
import com.jaknaeso.app.domain.usecase.MapToKoreanOrdinalWordUseCase
import com.jaknaeso.app.presentation.contract.ReportEffect
import com.jaknaeso.app.presentation.contract.ReportEvent
import com.jaknaeso.app.presentation.contract.ReportState
import com.jaknaeso.app.presentation.navigation.NO_BUNDLE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewmodel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getMemberSubmissionsResultUseCase: GetMemberSubmissionsResultUseCase,
    private val mapToKoreanOrdinalWord: MapToKoreanOrdinalWordUseCase,
    private val getCharacterReportUseCase: GetCharacterReportUseCase,
) :
    BaseViewModel<ReportEvent, ReportState, ReportEffect>() {
    override fun createInitialState(): ReportState {
        //모달 내용 초기화
        return ReportState(
            reportTitle = "첫번째 캐릭터", report = CharacterReport(
                null, null, null, null, null, null,
                emptyList(), emptyList(), emptyList(), "", emptyList()
            )
        )
    }

    override fun handleEvent(event: ReportEvent) {
        when (event) {
            is ReportEvent.GetCharactersList -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (event.bundleId == NO_BUNDLE_ID) { //바텀네비게이션으로 들어온 경우
                        getCharacters(true)
                    } else {
                        getCharacters(false)
                    }
                }
            }

            ReportEvent.GetFirstCharacterData -> {
                getFirstCharacterReport()
            }

            is ReportEvent.GetParticularCharacterData -> { //미션 라운드 아이템을 클릭해서 Report 페이지에 진입한 경우
                initializeParticularCharacterHistory(bundleId = event.bundleId)
            }

            is ReportEvent.SelectCharacterData -> { //캐릭터별 결과를 보는 경우
                viewModelScope.launch(Dispatchers.IO) {
                    val character = currentState.characters?.find { it.characterId.toString() == event.characterId }
                    setState { copy(reportTitle = event.characterNo) }
                    getSubmissionsResult(event.bundleId)
                    if (character?.isCompleted ?: false) {
                        setState { copy(isNoCharacterToShow = false) }
                        getParticularCharacter(event.characterId)
                    } else {
                        setState { copy(isNoCharacterToShow = true) }
                    }
                }
            }

            ReportEvent.ClickHome -> setEffect(ReportEffect.NavigateToHome)
            ReportEvent.ClickProfile -> setEffect(ReportEffect.NavigateToProfile)
        }
    }

    private fun getFirstCharacterReport() {
        viewModelScope.launch(Dispatchers.IO) {
            val character = currentState.characters?.get(0)
            if (character != null) {
                val isCharacterExisted =
                    isCharacterResultExisted(characterId = character.characterId.toString())
                getSubmissionsResult(character.bundleId.toString())
                if (isCharacterExisted) {
                    getParticularCharacter(character.characterId.toString())
                } else {
                    setState { copy(isNoCharacterToShow = true) }
                }
            } else {
                setState { copy(isNoCharacterToShow = true) }
            }
        }
    }

    private fun initializeParticularCharacterHistory(bundleId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val target = currentState.characters?.find { it.bundleId.toString() == bundleId }
            val isCharacterExisted = isCharacterResultExisted(characterId = target?.characterId.toString())

            setState { copy(reportTitle = mapToKoreanOrdinalWord(target!!.ordinalNumber)) } //레포트 타이틀 업데이트
            getSubmissionsResult(bundleId)

            if (isCharacterExisted) {
                getParticularCharacter(target?.characterId.toString()) //특정 캐릭터 결과 업데이트
            } else {
                setState { copy(isNoCharacterToShow = true) }
            }
        }
    }

    private suspend fun getParticularCharacter(characterId: String) {
        getCharacterReportUseCase(
            characterId = characterId
        ).asResult().collect { result ->
            when (result) {
                is Result.Error -> {}
                Result.Loading -> {}
                is Result.Success -> {
                    setState {
                        copy(
                            report = result.data,
                            reportTitle = "${result.data.characterNo}",
                            isNoCharacterToShow = false
                        )
                    }
                }
            }
        }
    }

    private suspend fun getCharacters(isGetFirstCharacter: Boolean) {
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
                is Result.Success -> {
                    setState { copy(characters = it.data) }
                    if (isGetFirstCharacter) {
                        setEffect(ReportEffect.CompletedLoadCharacterList)
                    }
                }
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
                is Result.Success -> setState { copy(submissionsResult = it.data, isLoading = false) }
            }
        }
    }

    private fun isCharacterResultExisted(characterId: String): Boolean {
        val isCharacterSurveyCompleted = currentState.characters?.find { it.characterId.toString() == characterId }
        if (isCharacterSurveyCompleted?.isCompleted == true) {
            return true
        }
        return false
    }

}
