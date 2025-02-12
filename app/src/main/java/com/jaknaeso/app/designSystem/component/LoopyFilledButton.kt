package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyFilledButton(
    text: String,
    icon: Painter? = null,
    iconColor: Color = Color.White,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean = true,
    filledColor: Color = ColorPalette.PrimaryBlue500,
    disabledColor: Color = ColorPalette.Neautral300,
    textColor: Color = Color.White,
    textStyle: TextStyle = TextStyles.subTitle01
) {
    val buttonModifier = Modifier.then(modifier ?: Modifier)
    FilledIconButton(
        onClick = onClick,
        modifier = buttonModifier.height(58.dp),
        colors = IconButtonColors(
            containerColor = filledColor,
            contentColor = filledColor,
            disabledContentColor = disabledColor,
            disabledContainerColor = disabledColor,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row {
            if (icon != null) {
                Icon(
                    painter = icon,
                    tint = iconColor,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
            Text("${text}", color = textColor, style = textStyle)
        }
    }
}


@Preview
@Composable
fun ButtonPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        LoopyFilledButton(
            "로그인하기",
            icon = painterResource(R.drawable.ic_kakao),
            iconColor = Color.Black,
            onClick = {},
            modifier = Modifier.fillMaxWidth(1f),
        )
        Row(modifier = Modifier.padding(4.dp)) {
            LoopyFilledButton(
                "작성 완료",
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.5f).padding(horizontal = 4.dp)
            )
            LoopyFilledButton(
                "넘어가기",
                onClick = {},
                modifier = Modifier.fillMaxWidth(1f).padding(horizontal = 4.dp),
                filledColor = ColorPalette.Neautral200,
                textColor = ColorPalette.Neautral600
            )
        }
    }
}
