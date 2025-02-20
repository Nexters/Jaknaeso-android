package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.TextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoopyTopBar(
    title: String,
    icon: Painter,
    containerColors: Color = Color.Transparent,
    iconColor: Color = Color.Black,
    textColor: Color = Color.Black,
    onClickIcon: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyles.title04,
                modifier = Modifier.fillMaxWidth(1f),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onClickIcon() })
        },
        colors = TopAppBarColors(
            containerColor = containerColors,
            scrolledContainerColor = containerColors,
            navigationIconContentColor = iconColor,
            titleContentColor = textColor,
            actionIconContentColor = iconColor
        )
    )
}

@Preview
@Composable
fun PreviewTopBar() {
    LoopyTopBar(title = "오늘의 질문", icon = painterResource(R.drawable.ic_back), onClickIcon = {})
}
