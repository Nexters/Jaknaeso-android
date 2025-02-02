package com.jaknaeso.app.presentation.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.jaknaeso.app.presentation.navigation.SetUpNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewmodel: MainViewmodel = hiltViewModel()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            val uiState = viewmodel.uiState.collectAsStateWithLifecycle()
            val initialRoute = remember { mutableStateOf(uiState.value.initialRoute) }

            SetUpNavGraph(
                navController = navHostController,
                startDestination = initialRoute.value,
            )
        }
    }
}
