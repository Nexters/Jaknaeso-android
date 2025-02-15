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
        loginScreen(
            navigateToHome = navController::navigateToHome,
            navigateToOnboarding = navController::navigateToOnboarding
        )
        onboardingScreen(navigateToHome = navController::navigateToHome)
        homeScreen(
            navigateToReport = navController::navigateToReport,
            navigateToProfile = navController::navigateToProfile,
            navigateToBalanceRound = navController::navigateToRound
        )
        roundScreen(
            navigateToBack = navController::popBackStack,
            navigateToBalanceRoundComplete = navController::navigateToRoundComplete
        )
        RoundCompleteScreen(navigateToHome = navController::navigateToHome)
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
