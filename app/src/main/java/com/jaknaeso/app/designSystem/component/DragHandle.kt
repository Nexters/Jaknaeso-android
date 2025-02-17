package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import kotlinx.coroutines.launch

@Composable
fun DragHandle(icon: Painter? = null, onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() }.fillMaxWidth().background(color = Color.White)
            .padding(bottom= 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = ColorPalette.Neautral400,
            )
        }
    }
}

@Preview
@Composable
fun DragHandlePreview(){
    DragHandle(icon = painterResource(R.drawable.ic_arrow_up)) {}
}
