package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles
import com.jaknaeso.app.domain.model.QuestionState
import com.jaknaeso.app.domain.model.Round


private data class QuestionItemState(
    val isEnabled: Boolean,
    val icon: Painter,
    val iconColor: Color,
    val filledColor: Color,
    val textColor: Color,
    val textStyle: TextStyle
)


@Composable
fun QuestionItem(round: Round, onClickItem: (state: QuestionState) -> Unit, index: Int) {
    val ROW = 5
    val size = (LocalConfiguration.current.screenWidthDp - (6 * 20)).div(ROW)
    val item = round.state.let {
        when (it) {
            QuestionState.FUTURE -> QuestionItemState(
                isEnabled = true,
                icon = painterResource(R.drawable.ic_lock),
                iconColor = ColorPalette.Neautral400,
                filledColor = ColorPalette.Neautral100,
                textColor = ColorPalette.Neautral400,
                textStyle = TextStyles.subTitle04
            )

            QuestionState.TODAY_LOCKED -> QuestionItemState(
                isEnabled = true,
                icon = painterResource(R.drawable.ic_lock),
                iconColor = ColorPalette.PrimaryBlue500,
                filledColor = ColorPalette.PrimaryBlue100,
                textColor = ColorPalette.Neautral900,
                textStyle = TextStyles.subTitle03
            )

            QuestionState.PAST -> QuestionItemState(
                isEnabled = true,
                icon = painterResource(R.drawable.ic_check),
                iconColor = ColorPalette.Neautral600,
                filledColor = ColorPalette.Neautral200,
                textColor = ColorPalette.Neautral600,
                textStyle = TextStyles.subTitle04
            )

            QuestionState.TODAY_COMPLETED -> QuestionItemState(
                isEnabled = true,
                icon = painterResource(R.drawable.ic_check),
                iconColor = ColorPalette.PrimaryBlue500,
                filledColor = ColorPalette.PrimaryBlue100,
                textColor = Color.Black,
                textStyle = TextStyles.subTitle03
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LoopyShapeFilledButton(
            onClick = { onClickItem(round.state) },
            enabled = item.isEnabled,
            icon = item.icon,
            shape = CircleShape,
            modifier = Modifier.sizeIn(minWidth = size.dp, minHeight = size.dp),
            iconColor = item.iconColor,
            disabledIconColor = item.iconColor,
            disabledColor = item.filledColor,
            filledColor = item.filledColor,
        )
        Text(
            "${index + 1}회차",
            style = item.textStyle,
            modifier = Modifier.padding(top = 6.dp),
            color = item.textColor
        )
    }
}
