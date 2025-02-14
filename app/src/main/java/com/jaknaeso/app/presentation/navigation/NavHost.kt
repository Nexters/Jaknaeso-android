package com.jaknaeso.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jaknaeso.app.presentation.view.SliderRoundScreen

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
            navigateToReport = navController::navigateToReport,
            navigateToProfile = navController::navigateToProfile,
            navigateToBalanceRound = navController::navigateToBalanceRound
        )
        balanceRoundScreen(
            navigateToBack = navController::popBackStack,
            navigateToBalanceRoundComplete = navController::navigateToRoundComplete
        )
        sliderRoundScreen(navigateToBack = navController::popBackStack, navigateToRoundComplete = navController::navigateToRoundComplete)
        balanceRoundCompleteScreen(navigateToHome = navController::navigateToHome)
        reportScreen(
            navigateToHome = navController::navigateToHome,
            navigateToProfile = navController::navigateToProfile
        )
        profileScreen(
            navigateToHome = navController::navigateToHome,
            navigateToReport = navController::navigateToReport
        )
    }
}
