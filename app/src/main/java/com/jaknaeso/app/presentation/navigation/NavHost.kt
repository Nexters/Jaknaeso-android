package com.jaknaeso.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginScreen(navigateToHome = navController::navigateToHome)
        homeScreen(
            navigateToHome = navController::navigateToHome,
            navigateToReport = {},
            navigateToProfile = {},
            navigateToRound = navController::navigateToOptionsRound
        )
        optionsRoundScreen(navigateToHome = navController::navigateToHome)
    }
}
