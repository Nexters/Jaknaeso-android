package com.jaknaeso.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavController.navigateToHome() = navigate("${Route.Home}")

fun NavGraphBuilder.perfumeSearchScreen(
    onBackClick: () -> Unit
) {
    composable(route = "${Route.Home}") {

    }
}
