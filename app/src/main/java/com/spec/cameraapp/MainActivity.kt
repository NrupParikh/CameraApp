package com.spec.cameraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
*  In this Main Activity we can do below functionality
    - Show Splash Screen using Splash API
    - Define the Main Screen
* */

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
                MainScreen()
            }
        }
    }
}