package com.jaknaeso.app.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.jaknaeso.app.data.model.ResponseResult
import com.jaknaeso.app.data.model.request.TokenRequest
import com.jaknaeso.app.domain.Result
import com.jaknaeso.app.domain.asResult
import com.jaknaeso.app.domain.repository.LoginRepository
import com.jaknaeso.app.presentation.common.BaseViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
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
                    Log.d("LoginViewmodel", "토큰값 유효2")
                }
            }
        } else {
            Log.e("LoginViewmodel", "카카오 계정으로 간다")
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    fun postAccessToken(token: String) {
        viewModelScope.launch {
            flow {
                val result = loginRepository.getMemberToken(TokenRequest(token))
                if (result.result == ResponseResult.SUCCESS.name) {
                    emit(result.data)
                } else {
                    throw Exception(message = result.error?.message ?: "")
                }
            }.asResult()
                .collectLatest {
                    when (it) {
                        is Result.Success -> {
                            if (it.data != null) {
                                loginRepository.saveAccessToken(it.data.accessToken)
                                loginRepository.saveRefreshToken(it.data.refreshToken)
                            }
                            setEffect(LoginEffect.NavigateToHome)
                        }

                        is Result.Error -> {}
                        Result.Loading -> {}
                    }
                }
        }
    }
}
