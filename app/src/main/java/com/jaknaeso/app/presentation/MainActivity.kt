package com.jaknaeso.app.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jaknaeso.app.designSystem.component.LoopyLoadingScreen
import com.jaknaeso.app.presentation.contract.MainEffect
import com.jaknaeso.app.presentation.navigation.SetUpNavGraph
import com.jaknaeso.app.presentation.navigation.navigateToLogin
import com.jaknaeso.app.presentation.viewmodel.MainViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val viewmodel: MainViewmodel = hiltViewModel()
            val navHostController = rememberNavController()
            val uiState by viewmodel.uiState.collectAsStateWithLifecycle()

            splashScreen.setKeepOnScreenCondition {
                viewmodel.branchInitialRoute()
                uiState.isInitialRoutingOngoing
            }

            LaunchedEffect(Unit) {
                viewmodel.effects.collect { effect ->
                    when (effect) {
                        MainEffect.NavigateToLogin -> navHostController.navigateToLogin()
                    }
                }
            }
            if (uiState.initialRoute != null) {
                SetUpNavGraph(
                    navController = navHostController,
                    startDestination = uiState.initialRoute!!,
                )
            } else {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { LoopyLoadingScreen() }
            }
        }
    }
}
