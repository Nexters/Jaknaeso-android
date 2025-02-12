package com.jaknaeso.app.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.authentication.LoopyApiResponse
import com.jaknaeso.app.domain.entity.request.TokenRequest
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.presentation.contract.LoginEffect
import com.jaknaeso.app.presentation.contract.LoginEvent
import com.jaknaeso.app.presentation.contract.LoginState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val application: Application,
    private val loginRepository: LoginRepository
) :
    BaseViewModel<LoginEvent, LoginState, LoginEffect>() {
    private val context = application.applicationContext
    override fun createInitialState(): LoginState {
        return LoginState
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.ClickKakaoLogin -> handleLogin()
        }
    }

    fun handleLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LoginViewmodel", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                viewModelScope.launch(Dispatchers.IO) { postAccessToken(token.accessToken) }
                Log.e("LoginViewmodel", "token값 유효", error)
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("LoginViewmodel", "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    viewModelScope.launch(Dispatchers.IO) { postAccessToken(token.accessToken) }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun postAccessToken(token: String) {
        viewModelScope.launch {
            val result = loginRepository.getMemberToken(TokenRequest(token))
            when (result) {
                is LoopyApiResponse.Error -> TODO()
                is LoopyApiResponse.Success -> {
                    if (result.data.data != null) {
                        Log.d(
                            "LoginViewmodel",
                            "accessToken:${result.data.data!!.accessToken}, refreshToken:${result.data.data!!.refreshToken}"
                        )
                        loginRepository.saveAccessToken(result.data.data!!.accessToken)
                        loginRepository.saveRefreshToken(result.data.data!!.refreshToken)
                    }
                    setEffect(LoginEffect.NavigateToHome)
                }
            }
        }
    }
}
