package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.LoopyTopBar
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun PrivateDataPolicyScreen(navigateToBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        LoopyTopBar(title = "개인정보처리방침", icon = painterResource(R.drawable.ic_back), onClickIcon = {})
        Column(Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text("1.개인정보 수집 항목", style = TextStyles.subTitle01)
            Text("당사는 회원가입 과정에서 다음과 같은 개인정보를 수집합니다:", style = TextStyles.body01)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(3.dp) // 도트 크기
                        .background(ColorPalette.Neautral700, shape = CircleShape) // 원 모양의 도트
                )
                Text("당사는 회원가입 과정에서 다음과 같은 개인정보를 수집합니다:", style = TextStyles.body02)
            }
        }
    }
}

@Preview
@Composable
fun PrivateDataPolicyPreview() {
    PrivateDataPolicyScreen({})
}

