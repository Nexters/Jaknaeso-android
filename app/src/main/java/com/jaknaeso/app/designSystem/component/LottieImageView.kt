package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.jaknaeso.app.R

@Composable
fun LottieImageView(rawFile: Int?, isFullScreen: Boolean = false, width: Dp = 240.dp, height: Dp = 240.dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawFile ?: R.raw.loopy_loading))
    val fullWidth = LocalConfiguration.current.screenWidthDp
    val modifier =
        if (isFullScreen) Modifier.width(fullWidth.dp).height(fullWidth.dp) else Modifier.width(width).height(height)
    Column(
        modifier = modifier.background(color = Color.Transparent, shape = RoundedCornerShape(25.dp))
    ) {
        if (rawFile != null) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                clipSpec = LottieClipSpec.Progress(0f, 1f),
                speed = 1.0f
            )
        }
    }
}

@Preview
@Composable
fun LottieImagePreview() {
    Column(Modifier.fillMaxSize(1f)) {
        LottieImageView(rawFile = R.raw.security2,isFullScreen = true)
    }
}
