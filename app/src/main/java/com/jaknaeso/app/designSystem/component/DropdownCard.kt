package com.jaknaeso.app.designSystem.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.R
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun DropdownCard(shellContent: @Composable () -> Unit, mainContent: @Composable () -> Unit, initialExpanded:Boolean) {
    var expanded by remember { mutableStateOf(initialExpanded) }

    Column(
        modifier = Modifier.wrapContentSize(Alignment.TopStart)
            .clickable { expanded = !expanded }.background(color = Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Column(Modifier.padding(vertical = 20.dp).padding(horizontal = 24.dp)) {
            shellContent()
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
            ) {
                mainContent()
            }
        }
    }
}

@Preview
@Composable
fun PreviewDropdownCard() {
    DropdownCard(initialExpanded = true, shellContent = {
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "3회차", style = TextStyles.subTitle03)
            Icon(painter = painterResource(R.drawable.ic_arrow_down), contentDescription = null, tint = Color.Black)
        }
    }, mainContent = {
        Column {
            Spacer(modifier = Modifier.fillMaxWidth(1f).height(16.dp))
            LoopyAssistChip(
                "2025.1.2",
                labelStyle = TextStyles.body02,
                filledColor = ColorPalette.Neautral200,
                labelColor = ColorPalette.Neautral700,
                shape = RoundedCornerShape(6.dp),
                enabled = false
            )
            Spacer(modifier = Modifier.fillMaxWidth(1f).height(16.dp))
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(bottom = 16.dp)) {
                Text("Q.", style = TextStyles.body01, modifier = Modifier.padding(end = 8.dp))
                Text(
                    "커리어를 향상시킬 수 있는 일자리이지만 가까운 사람들과 멀어져야한다면, 이 일자리를 선택하실 건가요?",
                    style = TextStyles.body01,
                    softWrap = true
                )
            }
            Spacer(
                modifier = Modifier.fillMaxWidth(1f).height(1.dp)
                    .background(color = ColorPalette.Neautral300)
            )
            Row(
                verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
            ) {
                Text("A.", style = TextStyles.body01, modifier = Modifier.padding(end = 8.dp))
                Text("주변 사람과 물리적으로 멀어지더라도, 커리어를 선택한다.", style = TextStyles.body01, softWrap = true)
            }
            Column(
                modifier = Modifier.background(
                    color = ColorPalette.Neautral100,
                    shape = RoundedCornerShape(12.dp)
                ).padding(vertical = 12.dp).padding(horizontal = 16.dp)
            ) {
                Text(
                    "회고",
                    style = TextStyles.body01,
                    color = ColorPalette.Neautral800,
                    modifier = Modifier.padding(bottom = 8.dp),
                    softWrap = true
                )
                Text(
                    "가까운 사람들과 물리적으로 멀어지더라도 그 관계가 사라지진 않음. 내 노력에 따라 관계는 달라질 수 있지만 커리어 기회는 원할 때 오는 게 아님",
                    style = TextStyles.body01, color = ColorPalette.Neautral700, softWrap = true
                )
            }
        }
    })
}
