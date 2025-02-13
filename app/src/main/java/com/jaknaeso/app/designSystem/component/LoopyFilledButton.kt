package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyFilledButton(
    text: String,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    leadingIconColor: Color = Color.Black,
    trailingIconColor: Color = Color.Black,
    iconSize:Dp = 20.dp,
    onClick: () -> Unit,
    height:Dp = 58.dp,
    modifier: Modifier,
    enabled: Boolean = true,
    filledColor: Color = ColorPalette.PrimaryBlue500,
    disabledColor: Color = ColorPalette.Neautral300,
    textColor: Color = Color.White,
    textStyle: TextStyle = TextStyles.subTitle01,
    borderColor: Color? = null
) {
    val borderStroke = BorderStroke(width = 1.4.dp, color = if (borderColor != null) borderColor else filledColor)
    val buttonModifier = Modifier.then(modifier)
    FilledIconButton(
        onClick = onClick,
        modifier = buttonModifier.height(height).border(borderStroke, shape = RoundedCornerShape(12.dp)),
        colors = IconButtonColors(
            containerColor = filledColor,
            contentColor = filledColor,
            disabledContentColor = disabledColor,
            disabledContainerColor = disabledColor,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.Center) {
            if (leadingIcon != null) {
                Icon(
                    painter = leadingIcon,
                    tint = leadingIconColor,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp).size(iconSize)
                )
            }
            Text("${text}", color = textColor, style = textStyle)
            if (trailingIcon != null) {
                Icon(
                    painter = trailingIcon,
                    tint = trailingIconColor,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp).size(iconSize)
                )
            }
        }
    }
}


@Preview
@Composable
fun ButtonPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        LoopyFilledButton(
            "로그인하기",
            leadingIcon = painterResource(R.drawable.ic_kakao),
            leadingIconColor = Color.Black,
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
                textColor = ColorPalette.Neautral600,
                borderColor = ColorPalette.Neautral800,
                trailingIcon = painterResource(R.drawable.ic_kakao),
                trailingIconColor = ColorPalette.Neautral600,
                height = 50.dp
            )
        }
    }
}
