package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun ErrorInfoView(title: String, message: String, onClickReLoad: () -> Unit, onClickHome: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(title, style = TextStyles.title02)
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(12.dp))
        Text(message, style = TextStyles.subTitle03, color = ColorPalette.Neautral600)
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(24.dp))
        LoopyFilledButton(
            enabled = true,
            text = "화면 새로고침",
            onClick = onClickReLoad,
            modifier = Modifier.sizeIn(minWidth = 120.dp),
            filledColor = ColorPalette.Neautral400,
            textColor = Color.White,
            height = 37.dp
        )
        Spacer(modifier = Modifier.height(10.dp))
        LoopyFilledButton(
            enabled = true,
            text = "홈으로",
            onClick = onClickHome,
            modifier = Modifier.sizeIn(minWidth = 70.dp),
            filledColor = ColorPalette.Neautral700,
            textColor = Color.White,
            height = 37.dp
        )
    }
}

@Preview
@Composable
fun ErrorInfoPreview() {
    ErrorInfoView(title = "오류가 발생했어요!", message = "일시적인 오류가 발생했어요.\n화면을 새로고침 해주세요.", {}, {})
}
