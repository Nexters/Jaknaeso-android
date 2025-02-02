package com.jaknaeso.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.jaknaeso.app.R


data class BottomNavigationItem(
    val label: String = "",
    val icon: Painter,
    val route: Route
)

@Composable
fun bottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            label = "Home",
            icon = painterResource(R.drawable.ic_home),
            route = Route.Home
        ),
        BottomNavigationItem(
            label = "Report",
            icon = painterResource(R.drawable.ic_document),
            route = Route.Report
        ),
        BottomNavigationItem(
            label = "Profile",
            icon = painterResource(R.drawable.ic_person),
            route = Route.MyPage
        ),
    )
}
