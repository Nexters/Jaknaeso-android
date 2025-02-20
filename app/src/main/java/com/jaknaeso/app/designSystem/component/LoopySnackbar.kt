package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun Loopysnackbar(snackbarHostState: SnackbarHostState, content: @Composable () -> Unit) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.height(58.dp)
    ) {
        Column(
            modifier = Modifier.background(
                color = ColorPalette.GlassBlack.copy(alpha = 0.95f),
                shape = RoundedCornerShape(12.dp)
            ).fillMaxWidth(1f).fillMaxHeight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun LoopysnackbarPrevie() {
    val snackbarHostState = remember { SnackbarHostState() }
    Loopysnackbar(snackbarHostState = snackbarHostState) {
        Text(
            "하루에 한 회차씩 답변할 수 있어요",
            style = TextStyles.subTitle04,
            color = Color.White,
            modifier = Modifier.padding(vertical = 25.dp).fillMaxWidth(1f),
            textAlign = TextAlign.Center
        )
    }

}
