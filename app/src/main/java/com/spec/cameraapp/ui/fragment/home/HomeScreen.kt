package com.spec.cameraapp.ui.fragment.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.ui.utils.LBL_CREATE_NEW_PROJECT

@Composable
fun HomeScreen(onClickToCaptureImage: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(16.dp))
        ElevatedButton(onClick = onClickToCaptureImage) {
            Text(text = LBL_CREATE_NEW_PROJECT)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CameraAppTheme {
        HomeScreen()
    }
}