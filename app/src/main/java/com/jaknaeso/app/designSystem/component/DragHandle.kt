package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun DragHandle(onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable { onClick() }.fillMaxWidth().height(42.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_upside),
            contentDescription = null,
            tint = ColorPalette.Neautral400,
        )
    }
}
