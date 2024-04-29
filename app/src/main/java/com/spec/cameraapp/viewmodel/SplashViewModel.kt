package com.spec.cameraapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            delay(2000)
            splashScreenFlow.value = false
        }
    }
}