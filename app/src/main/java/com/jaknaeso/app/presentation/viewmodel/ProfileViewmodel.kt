package com.jaknaeso.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.entity.ResponseResult
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.usecase.DeleteMemberUseCase
import com.jaknaeso.app.domain.usecase.GetMemberUseCase
import com.jaknaeso.app.domain.usecase.LogoutUseCase
import com.jaknaeso.app.presentation.contract.ProfileEffect
import com.jaknaeso.app.presentation.contract.ProfileEvent
import com.jaknaeso.app.presentation.contract.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val deleteMemberUseCase: DeleteMemberUseCase,
    private val getMemberUseCase: GetMemberUseCase
) :
    BaseViewModel<ProfileEvent, ProfileState, ProfileEffect>() {
    override fun createInitialState(): ProfileState {
        return ProfileState("", "")
    }

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.GetMemberInfo -> getMemberInfo()
            ProfileEvent.ClickPolicy -> {}
            ProfileEvent.ControlNotification -> {}
            ProfileEvent.DeleteMember -> deleteMember()
            ProfileEvent.LogOutMember -> logout()
        }
    }

    fun deleteMember() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMemberUseCase().asResult().collect {
                when (it) {
                    is Result.Error -> {
                        if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                            setEffect(ProfileEffect.NavigateToLogin)
                        }
                    }

                    Result.Loading -> {}
                    is Result.Success -> {
                        setEffect(ProfileEffect.NavigateToLogin)
                    }
                }
            }
        }
    }

    fun getMemberInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            getMemberUseCase().asResult().collect {
                when (it) {
                    is Result.Error -> {
                        if (it.exception.message == ResponseResult.REFRESH_FAILED.name) {
                            setEffect(ProfileEffect.NavigateToLogin)
                        }
                        Log.d("ProfileViewmodel", "exception:${it.exception.message}")
                    }

                    Result.Loading -> {}
                    is Result.Success -> setState { copy(name = it.data.name, email = it.data.email) }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) { logoutUseCase() }
        setEffect(ProfileEffect.NavigateToLogin)
    }
}
