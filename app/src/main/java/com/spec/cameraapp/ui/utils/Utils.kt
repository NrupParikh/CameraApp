package com.spec.cameraapp.ui.utils

fun getScreenNameFromRoute(currentRoute: String): String {
    return when (currentRoute) {
        ROUTE_HOME -> TITLE_HOME
        ROUTE_CAPTURE_IMAGE -> TITLE_CAPTURE_IMAGE
        ROUTE_IMAGE_EDIT -> TITLE_EDIT_IMAGE
        else -> ""
    }
}