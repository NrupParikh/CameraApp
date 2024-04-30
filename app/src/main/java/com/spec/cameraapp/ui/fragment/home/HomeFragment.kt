package com.spec.cameraapp.ui.fragment.home

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.spec.cameraapp.ui.theme.CameraAppTheme

@Composable
fun HomeFragment(
    onClickToCaptureImage: () -> Unit = {}
) {
    HomeScreen(onClickToCaptureImage = onClickToCaptureImage)
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeFragmentPreview() {
    CameraAppTheme {
        HomeFragment()
    }
}