package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun LottieImageView(rawFile: Int?, width:Dp=240.dp, height:Dp=240.dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawFile?:R.raw.loopy_loading))
    Column(
        modifier = Modifier.width(width).height(height)
            .background(color = Color.Transparent, shape = RoundedCornerShape(25.dp))
    ) {
        if(rawFile != null){
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                clipSpec = LottieClipSpec.Progress(0.5f, 0.75f)
            )
        }
    }
}

@Preview
@Composable
fun LottieImagePreview() {
    LottieImageView(R.raw.warning)
}
