package com.jaknaeso.app.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jaknaeso.app.designSystem.theme.ColorPalette

@Composable
fun LoopyBottomNavBar(
    navigateToHome: () -> Unit,
    navigateToReport: () -> Unit,
    navigateToProfile: () -> Unit,
    currentRoute: Route
) {
    var navigationSelectedItem by remember {
        mutableStateOf(
            when (currentRoute) {
                Route.Home -> 0
                Route.Report -> 1
                Route.Profile -> 2
                else -> {}
            }
        )
    }


    NavigationBar(
        contentColor = Color.White,
        containerColor = Color.White,
    ) {
        bottomNavigationItems().forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = {},
                icon = {
                    Icon(
                        painter = bottomNavigationItem.icon,
                        contentDescription = null,
                    )
                },
                onClick = {
                    when (bottomNavigationItem.route) {
                        Route.Home -> navigateToHome()
                        Route.Report -> navigateToReport()
                        Route.Profile -> navigateToProfile()
                        else -> {}
                    }
                    navigationSelectedItem = index
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = ColorPalette.Neautral500,
                    unselectedTextColor = ColorPalette.Neautral700,
                    disabledIconColor = ColorPalette.Neautral700,
                    disabledTextColor = ColorPalette.Neautral700
                )
            )
        }
    }
}

@Preview
@Composable
fun NavigationBarPreview() {
    LoopyBottomNavBar({}, {}, {}, Route.Home)
}
