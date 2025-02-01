package com.jaknaeso.app.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.jaknaeso.app.presentation.login.loginScreen

fun NavController.navigateToHome() = navigate("${Route.Home}")
fun NavController.navigateToLogin() = navigate("${Route.Login}")

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginScreen(navigateToHome = { navController.navigateToHome() })
    }
}
