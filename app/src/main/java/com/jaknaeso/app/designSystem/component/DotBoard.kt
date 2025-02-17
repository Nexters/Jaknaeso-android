package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun DotBoard(titleContent: String, contents: List<String>) {
    Column(Modifier.background(color = Color.Transparent)) {
        Text(text = titleContent, style = TextStyles.title03, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        contents.forEach { text ->
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.background(color = Color.Transparent)) {
                Column(Modifier.padding(top = 10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(3.dp) // 도트 크기
                            .background(ColorPalette.Neautral700, shape = CircleShape) // 원 모양의 도트
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // 도트와 텍스트 사이 간격
                Text(text, style = TextStyles.subTitle02, color = ColorPalette.Neautral700, softWrap = true)
            }
        }
    }
}

@Preview
@Composable
fun DotBoardPreview(){
    DotBoard(titleContent = "주요 특징", contents = listOf("창의적인 해결책을 찾고 새로운 아이디어를 탐구하는 것을 좋아해요.","외부의 기대보다 자신의 내면적 가치와 신념을 따르는 것을 중요하게 생각해요."))
}
