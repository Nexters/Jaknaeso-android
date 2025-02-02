package com.jaknaeso.app.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jaknaeso.app.designSystem.component.LoopySuggestionChip
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = ColorPalette.Neautral50)) {
        LoopySuggestionChip(
            "1번째 캐릭터",
            labelStyle = TextStyles.subTitle04,
            filledColor = ColorPalette.Neautral200,
            labelColor = ColorPalette.Neautral700
        )
    }
}
