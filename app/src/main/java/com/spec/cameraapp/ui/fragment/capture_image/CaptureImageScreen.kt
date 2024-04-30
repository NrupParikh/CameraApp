package com.spec.cameraapp.ui.fragment.capture_image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.spec.cameraapp.ui.theme.CameraAppTheme

@Composable
fun CaptureImageScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Capture Image Screen")
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