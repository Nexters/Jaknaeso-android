package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LottieImageView
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun RoundCompleteScreen(navigateToHome: () -> Unit) {
    Box(Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 20.dp)) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "오늘의 질문 답변을 완료했어요!", style = TextStyles.title02, softWrap = true)
            Spacer(Modifier.height(20.dp))
            Text(
                text = "캐릭터 완성까지 N회 남았어요",
                style = TextStyles.subTitle02,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Spacer(Modifier.height(40.dp))
            LottieImageView()
        }
        Column(
            Modifier.fillMaxSize().padding(bottom = 62.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoopyFilledButton(
                "완료",
                leadingIconColor = Color.Black,
                onClick = navigateToHome,
                modifier = Modifier.fillMaxWidth(1f),
            )
        }
    }
}
