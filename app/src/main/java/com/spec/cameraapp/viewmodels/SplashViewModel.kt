package com.spec.cameraapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spec.cameraapp.ui.utils.SPLASH_SCREEN_TIME_OUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Showing Splash screen for 2 seconds

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val splashScreenFlow = MutableStateFlow(true)
    val isSplashScreenShowing = splashScreenFlow.asStateFlow()


    init {
        viewModelScope.launch {
            delay(SPLASH_SCREEN_TIME_OUT)
            splashScreenFlow.value = false
        }
    }
}