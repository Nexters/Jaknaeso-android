package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FilterChip
import androidx.compose.material.Text
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Icon
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
fun LoopyAssistChip(
    label: String,
    shape: Shape,
    enabled: Boolean = true,
    onClick:()->Unit = {},
    trailingIcon: Painter? = null,
    trailingIconColor: Color = ColorPalette.Neautral600,
    labelStyle: TextStyle = TextStyles.subTitle03,
    labelIconColor: Color = ColorPalette.PrimaryBlue500,
    labelColor: Color = ColorPalette.PrimaryBlue500,
    filledColor: Color = ColorPalette.PrimaryBlue100,
    disabledLabelColor: Color = ColorPalette.Neautral700,
    disabledIconColor: Color = ColorPalette.Neautral700,
    disabledColor: Color = ColorPalette.Neautral200,
    modifier: Modifier? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val borderColor = remember { mutableStateOf(if (enabled) filledColor else disabledColor) }
    AssistChip(
        modifier = Modifier.height(33.dp).then(modifier ?: Modifier),
        label = { Text(label, style = labelStyle, color = labelColor) },
        onClick = onClick,
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(painter = trailingIcon, contentDescription = null)
            }
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = filledColor,
            labelColor = labelColor,
            leadingIconContentColor = labelIconColor,
            trailingIconContentColor = trailingIconColor,
            disabledContainerColor = filledColor,
            disabledLabelColor = filledColor,
            disabledLeadingIconContentColor = disabledIconColor,
            disabledTrailingIconContentColor = disabledLabelColor
        ),
        enabled = enabled,
        border = BorderStroke(width = 0.dp, color = borderColor.value),
        shape = shape,
        interactionSource = interactionSource,
    )
}

@Preview
@Composable
fun PreviewLoopyAssistChip() {
    Column(Modifier.fillMaxWidth(1f)) {
        LoopyAssistChip(
            "1번째 캐릭터",
            labelStyle = TextStyles.subTitle04,
            filledColor = ColorPalette.Neautral200,
            labelColor = ColorPalette.Neautral700,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = painterResource(R.drawable.ic_arrow_down),
            trailingIconColor = ColorPalette.Neautral600
        )
        LoopyAssistChip(
            "1번째 캐릭터",
            labelStyle = TextStyles.subTitle04,
            filledColor = ColorPalette.PrimaryBlue100,
            labelColor = ColorPalette.PrimaryBlue500,
            shape = RoundedCornerShape(8.dp),
            trailingIcon = painterResource(R.drawable.ic_arrow_down),
            trailingIconColor = ColorPalette.Neautral600,
            modifier = Modifier.fillMaxWidth(1f)
        )
    }
}
