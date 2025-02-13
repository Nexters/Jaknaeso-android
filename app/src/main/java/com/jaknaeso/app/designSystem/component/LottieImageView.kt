package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun LottieImageView(){
    Column(
        modifier = Modifier.fillMaxSize(0.56f).aspectRatio(1f).sizeIn(minWidth = 220.dp, minHeight = 220.dp)
            .background(color = ColorPalette.Neautral300, shape = RoundedCornerShape(25.dp))
    ) {

    }
}
