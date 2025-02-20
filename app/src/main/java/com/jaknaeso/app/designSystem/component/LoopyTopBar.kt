package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyTopBar(
    title: String,
    icon: Painter,
    containerColors: Color = Color.Transparent,
    iconColor: Color = Color.Black,
    textColor: Color = Color.Black,
    onClickIcon: () -> Unit
) {
    Column(Modifier.fillMaxWidth(1f).padding(vertical = 15.dp, horizontal = 20.dp)) {
        Box(
            Modifier.fillMaxWidth(1f).background(color = containerColors),
            contentAlignment = Alignment.Center
        ) {
            Row(Modifier.fillMaxWidth(1f), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onClickIcon() },
                    tint = iconColor,

                    )
            }
            Box(Modifier.fillMaxWidth(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = title,
                    style = TextStyles.title04,
                    modifier = Modifier.fillMaxWidth(1f),
                    textAlign = TextAlign.Center,
                    color = textColor
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    LoopyTopBar(title = "오늘의 질문", icon = painterResource(R.drawable.ic_back), onClickIcon = {})
}
