package com.spec.cameraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.spec.cameraapp.ui.components.currentRoute
import com.spec.cameraapp.ui.navigation.NavigationGraph
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.ui.topbar.MyTopAppBar
import com.spec.cameraapp.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        // Installing Splash Screen
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isSplashScreenShowing.value
        }


        setContent {
            CameraAppTheme {

                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        MyTopAppBar(navController = navController)
                    },
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            NavigationGraph(navController = navController)
                        }
                    }
                )
            }
        }
    }
}