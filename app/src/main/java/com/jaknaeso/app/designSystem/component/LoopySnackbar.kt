package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun Loopysnackbar(snackbarHostState: SnackbarHostState, content: @Composable () -> Unit) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(20.dp)

    ) {
        Column(
            modifier = Modifier.background(
                color = ColorPalette.GlassBlack.copy(alpha = 0.95f),
                shape = RoundedCornerShape(20.dp)
            )
                .fillMaxWidth(1f)
        ) {
            content()
        }

    }
}
