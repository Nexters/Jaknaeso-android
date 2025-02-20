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
            navigateToInformation = navController::navigateToInformation,
            navigateToPolicy = navController::navigateToPrivateDataPolicy
        )
        informationScreen(navigateToOnboarding = navController::navigateToOnboarding)
        onboardingScreen(
            navigateToLogin = navController::navigateToLogin,
            navigateToReport = navController::navigateToReport
        )
        homeScreen(
            navigateToLogin = navController::navigateToLogin,
            navigateToReport = navController::navigateToReport,
            navigateToProfile = navController::navigateToProfile,
            navigateToBalanceRound = navController::navigateToRound
        )
        roundScreen(
            navigateToLogin = navController::navigateToLogin,
            navigateToBack = navController::popBackStack,
            navigateToBalanceRoundComplete = navController::navigateToRoundComplete
        )
        RoundCompleteScreen(navigateToHome = navController::navigateToHome)
        reportScreen(
            navigateToLogin = navController::navigateToLogin,
            navigateToHome = navController::navigateToHome,
            navigateToProfile = navController::navigateToProfile
        )
        profileScreen(
            navigateToHome = navController::navigateToHome,
            navigateToReport = navController::navigateToReport,
            navigateToLogin = navController::navigateToLogin,
            navigateToPolicy = navController::navigateToPrivateDataPolicy
        )
        privateDataPolicyScreen(navigateToBack = navController::popBackStack)
    }
}
