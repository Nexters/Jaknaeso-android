package com.jaknaeso.app.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaknaeso.app.designSystem.theme.ColorPalette
import com.jaknaeso.app.designSystem.theme.TextStyles

@Composable
fun LoopyTabBar(initialPage:Int, onPage: (index: Int) -> Unit, tabBarTitles: List<String>) {
    var selectedIndex by remember { mutableStateOf(initialPage) }

    Column(horizontalAlignment = Alignment.Start) {
        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            modifier = Modifier.background(color = Color.Transparent).padding(start = 16.dp),
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(currentTabPosition = it[selectedIndex])
                        .padding(horizontal = 8.dp),
                    height = 2.dp,
                    color = ColorPalette.PrimaryBlue500
                )
            },
            contentColor = ColorPalette.Neautral0,
            backgroundColor = ColorPalette.Neautral0,
            edgePadding = 0.dp
        ) {
            tabBarTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        onPage(index)
                    },
                    enabled = true,
                    selectedContentColor = Color.Transparent,
                    unselectedContentColor = Color.Transparent,
                    modifier = Modifier.background(color = ColorPalette.Neautral0).padding(horizontal = 8.dp).clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {  }
                ) {
                    Text(
                        text = title,
                        style = TextStyles.subTitle03,
                        color = if (selectedIndex == index) Color.Black else ColorPalette.Neautral500,
                        modifier = Modifier.padding(vertical = 11.dp)
                    )
                }
            }
        }
        Divider(color = ColorPalette.Neautral400, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
    }
}

@Preview
@Composable
fun LoopTabRowPreview() {
    LoopyTabBar(0,{}, listOf("캐릭터 분석", "나의 답변 모아보기"))
}
