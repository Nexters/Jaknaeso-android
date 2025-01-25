package com.jaknaeso.app.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jaknaeso.app.presentation.Route

fun NavController.navigateToHome() = navigate("${Route.Home}")

fun NavGraphBuilder.perfumeSearchScreen(
    onBackClick: () -> Unit
) {
    composable(route = "${Route.Home}") {

    }
}
