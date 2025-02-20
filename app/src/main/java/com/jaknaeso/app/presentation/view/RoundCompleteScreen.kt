package com.jaknaeso.app.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.component.LoopyFilledButton
import com.jaknaeso.app.designSystem.component.LottieImageView
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun RoundCompleteScreen(navigateToHome: () -> Unit, remaingRounds: Int) {
    Column(
        Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.fillMaxWidth().weight(1f).windowInsetsPadding(WindowInsets.statusBars)
                .windowInsetsPadding(WindowInsets.navigationBars),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${15-remaingRounds}차 질문에\n답변을 완료했어요!", style = TextStyles.title02, softWrap = true, textAlign = TextAlign.Center)
            Spacer(Modifier.height(20.dp))
            Text(
                text = "캐릭터 완성까지 ${remaingRounds}회 남았어요",
                style = TextStyles.subTitle02,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Spacer(Modifier.height(40.dp))
            LottieImageView(R.raw.paper_pollen, isFullScreen = true)
        }
        Column(
            Modifier.padding(bottom = 28.dp),
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

@Composable
@Preview
fun RoundCompleteScreenPreview() {
    RoundCompleteScreen({}, 0)
}
