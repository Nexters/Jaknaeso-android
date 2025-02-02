package com.jaknaeso.app.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun LoopyBottomNavBar(navigateToHome: () -> Unit, navigateToReport: () -> Unit, navigateToProfile: () -> Unit) {
    var navigationSelectedItem by remember { mutableStateOf(0) }

    NavigationBar(contentColor = Color.Transparent, containerColor = Color.Transparent) {
        bottomNavigationItems().forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = {},
                icon = { Icon(painter = bottomNavigationItem.icon, contentDescription = null) },
                onClick = {
                    when (bottomNavigationItem.route) {
                        Route.Home -> navigateToHome()
                        Route.Report -> navigateToReport()
                        Route.MyPage -> navigateToProfile()
                        else -> {}
                    }
                    navigationSelectedItem = index
                }
            )
        }
    }
}
