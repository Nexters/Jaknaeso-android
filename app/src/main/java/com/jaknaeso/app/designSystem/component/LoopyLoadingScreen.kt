package com.jaknaeso.app.designSystem.component

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
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyLoadingScreen() {
    Box(modifier = Modifier.fillMaxSize().background(color = Color.Transparent), contentAlignment = Alignment.Center) {
        LottieImageView(rawFile = R.raw.loopy_loading, 200.dp, 200.dp)
    }
}

@Composable
@Preview
fun LoopyLoadingScreenPreview(){
    LoopyLoadingScreen()
}
