package com.spec.cameraapp.ui.fragment.capture_image

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.spec.cameraapp.ui.theme.CameraAppTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CaptureImageScreen() {

    val hasCameraPermission = remember { mutableStateOf(false) }

    val cameraPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        when {
            isGranted -> hasCameraPermission.value = true
            else -> hasCameraPermission.value = false
        }
    }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.hasPermission && cameraPermissionState.shouldShowRationale) {
            // Show rationale if needed
            cameraPermissionState.launchPermissionRequest()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            if (hasCameraPermission.value) {
                // Your camera composable or screen
                Text(text = "CAMERA IS NOW OPEN")
            } else {
                // Your permission request explanation composable
                Text(text = "ALLOW CAMERA PERMISSION FOR THIS FEATURE")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaptureImageScreenPreview() {
    CameraAppTheme {
        CaptureImageScreen()
    }
}