package com.spec.cameraapp.ui.fragment.image_editing

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.viewmodels.MainViewModel

/*
*  In this Image Editing Screen we can do below functionality
*   - Display Captured Image or selected image
*   - Perform filtering
* */

@Composable
fun ImageEditingScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {

    val photoUri = mainViewModel.imageUrl.observeAsState(initial = "")
    Log.d("TAG", "DURL $photoUri")

//    val photoUri = "content://media/external/images/media/1000060027"
//    val photoUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000058380"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LoadImageFromUri(
            context = LocalContext.current,
            imageUri = photoUri.value,
            transformation = null,
            imageSize = 500.dp,
            scaleType = ContentScale.Crop
        )
    }
}