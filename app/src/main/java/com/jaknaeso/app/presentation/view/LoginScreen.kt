package com.jaknaeso.app.presentation.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.presentation.contract.LoginEffect
import com.jaknaeso.app.presentation.viewmodel.LoginViewmodel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

@Composable
fun LoginScreen(
    navigateToOnBoarding: () -> Unit,
    navigateToHome: () -> Unit,
    viewmodel: LoginViewmodel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewmodel.effects.collect { effects ->
            when (effects) {
                LoginEffect.NavigateToHome -> {
                    navigateToHome()
                }

                LoginEffect.NavigateToOnboarding -> {
                    navigateToOnBoarding()
                }
            }
        }
    }

    fun handleLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("LoginViewmodel", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                viewmodel.postAccessToken(token.accessToken)
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
                    viewmodel.postAccessToken(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).padding(bottom = 36.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            Image(painterResource(R.drawable.ic_loopy), contentDescription = null)
            Spacer(Modifier.height(20.dp))
            Text(
                "나의 가치관을 찾는\n새로운 여정",
                style = TextStyles.title01,
                color = ColorPalette.Neautral800,
                textAlign = TextAlign.Center
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LoopyFilledButton(
                text = "카카오로 시작하기",
                leadingIcon = painterResource(R.drawable.ic_kakao),
                leadingIconColor = Color.Black,
                filledColor = ColorPalette.Kakao,
                onClick = { handleLogin() },
                textColor = Color.Black,
                modifier = Modifier.fillMaxWidth(1f)
            )
            Spacer(Modifier.height(36.dp))
            Text(
                "로그인하시면 Loopy의 개인정보처리방침에 동의하는 것으로 간주합니다.\n로그인 오류시 문의 app.jaknaeso@gmail.com",
                style = TextStyle(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 15.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).padding(bottom = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight(0.8f)
        ) {
            Image(painterResource(R.drawable.ic_loopy), contentDescription = null)
            Spacer(Modifier.height(20.dp))
            Text(
                "나의 가치관을 찾는\n새로운 여정",
                style = TextStyles.title01,
                color = ColorPalette.Neautral800,
                textAlign = TextAlign.Center
            )
        }
        LoopyFilledButton(
            text = "카카오로 시작하기",
            leadingIcon = painterResource(R.drawable.ic_kakao),
            leadingIconColor = Color.Black,
            filledColor = ColorPalette.Kakao,
            onClick = { },
            textColor = Color.Black,
            modifier = Modifier.fillMaxWidth(1f)
        )
        Text(
            "로그인하시면 Loopy의 개인정보처리방침에 동의하는 것으로 간주합니다.\n로그인 오류시 문의 app.jaknaeso@gmail.com",
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.W700,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}
