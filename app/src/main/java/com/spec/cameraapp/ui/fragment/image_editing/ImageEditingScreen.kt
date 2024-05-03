package com.spec.cameraapp.ui.fragment.image_editing

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.spec.cameraapp.R
import com.spec.cameraapp.viewmodels.MainViewModel

/*
*  In this Image Editing Screen we can do below functionality
*   - Display Captured Image or selected image
*   - Perform filtering
* */

@Composable
fun ImageEditingScreen(mainViewModel: MainViewModel) {

    val photoUri = "content://media/external/images/media/1000058790"
//    val photoUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000058380"


    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = photoUri)
            .build()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painter, contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .border(4.dp, Color.Gray),
            contentScale = ContentScale.Crop
        )

        Text(text = stringResource(id = R.string.lbl_edit_image_here))
    }
}