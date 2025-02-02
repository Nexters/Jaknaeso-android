package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyShapeFilledButton(
    enabled: Boolean,
    icon: Painter? = null,
    label: String = "",
    shape: Shape,
    labelStyle: TextStyle = TextStyles.subTitle03,
    labelColor: Color = ColorPalette.PrimaryBlue500,
    filledColor: Color = ColorPalette.PrimaryBlue100,
    iconColor:Color = ColorPalette.PrimaryBlue500,
    disabledColor: Color = ColorPalette.Neautral100,
    disabledIconColor:Color = ColorPalette.Neautral400
) {
    val iconColor = remember { mutableStateOf(if (enabled) iconColor else disabledIconColor) }

    FilledIconButton(
        onClick = {},
        colors = IconButtonColors(
            containerColor = filledColor,
            contentColor = filledColor,
            disabledContainerColor = disabledColor,
            disabledContentColor = disabledColor
        ),
        enabled = enabled,
        shape = shape,
        modifier = Modifier.sizeIn(minWidth = 58.dp, minHeight = 58.dp)
    ){
        if(icon != null){
        Icon(painter = icon, contentDescription = null, tint = iconColor.value)
        }
        Text(text = label, style = labelStyle, color = labelColor)
    }
}

@Preview
@Composable
fun PreviewFilterChip() {
    Column {
        LoopyShapeFilledButton(enabled = true, icon = painterResource(R.drawable.ic_lock), shape = CircleShape)
        LoopyShapeFilledButton(enabled = false, icon = painterResource(R.drawable.ic_lock), shape = CircleShape)
    }
}
