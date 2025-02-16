package com.jaknaeso.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.usecase.DeleteMemberUseCase
import com.jaknaeso.app.presentation.contract.ProfileEffect
import com.jaknaeso.app.presentation.contract.ProfileEvent
import com.jaknaeso.app.presentation.contract.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewmodel @Inject constructor(private val deleteMemberUseCase: DeleteMemberUseCase) :
    BaseViewModel<ProfileEvent, ProfileState, ProfileEffect>() {
    override fun createInitialState(): ProfileState {
        return ProfileState
    }

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.ClickPolicy -> {}
            ProfileEvent.ControlNotification -> {}
            ProfileEvent.DeleteMember -> deleteMember()
            ProfileEvent.LogOutMember -> {}
        }
    }

    fun deleteMember() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMemberUseCase().asResult().collect {
                when (it) {
                    is Result.Error -> {
                    }

                    Result.Loading -> {}
                    is Result.Success -> setEffect(ProfileEffect.NavigateToLogin)
                }
            }
        }
    }
}
