package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun DotIndicator(
    totalPage: Int,
    currentPage: Int,
    currentPageColor: androidx.compose.ui.graphics.Color = ColorPalette.PrimaryBlue500,
    otherPageColor: androidx.compose.ui.graphics.Color = ColorPalette.Neautral300,
) {

    Row {
        for (i in 0 until totalPage) {
            val color = if (i == currentPage) currentPageColor else otherPageColor
            Box(
                modifier = Modifier
                    .size(10.dp) // 도트 크기
                    .background(color, shape = CircleShape) // 원 모양의 도트
            )
            if (i < totalPage - 1) {
                Spacer(Modifier.width(14.dp))
            }
        }
    }
}

@Preview
@Composable
fun DotIndicator(){
    DotIndicator(4,1)
}
