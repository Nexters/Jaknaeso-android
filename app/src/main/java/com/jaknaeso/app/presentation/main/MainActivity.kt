package com.jaknaeso.app.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jaknaeso.app.presentation.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewmodel: MainViewmodel = hiltViewModel()
            val navHostController = rememberNavController()
            val uiState = viewmodel.uiState.collectAsStateWithLifecycle()

            SetUpNavGraph(
                navController = navHostController,
                startDestination = uiState.value.initialRoute,
            )
        }
    }
}
