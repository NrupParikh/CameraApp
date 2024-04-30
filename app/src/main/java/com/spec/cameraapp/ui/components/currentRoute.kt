package com.spec.cameraapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

// Get the current Route

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}