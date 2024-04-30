package com.spec.cameraapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.spec.cameraapp.ui.fragment.capture_image.CaptureImageFragment
import com.spec.cameraapp.ui.fragment.home.HomeFragment
import com.spec.cameraapp.ui.utils.ROUTE_CAPTURE_IMAGE

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Home.route) {
            HomeFragment() {
                navController.navigate(route = ROUTE_CAPTURE_IMAGE)
            }
        }
        composable(route = Route.CaptureImage.route) {
            CaptureImageFragment()
        }
    }
}