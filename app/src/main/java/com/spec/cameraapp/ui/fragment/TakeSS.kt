package com.spec.cameraapp.ui.fragment

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.MutableLiveData
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.Transformation
import com.spec.cameraapp.R
import com.spec.cameraapp.ui.components.screenshots.capturable
import com.spec.cameraapp.ui.components.screenshots.rememberCaptureController
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.utils.transformations.ColorFilterTransformation
import com.spec.cameraapp.utils.transformations.MaskTransformation
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TakeSS() {

    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()
    var ssBitmap: ImageBitmap? by remember { mutableStateOf(null) }
//    val imageUri = "/data/user/0/com.spec.cameraapp/cache/_image1716343424"
    val imageUri = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg"
    val context = LocalContext.current

    val listOfTransformations = remember { mutableListOf<Transformation>() }
    val colorValueTrans = remember { MutableLiveData(Color.Yellow) }

    CameraAppTheme {
        val imageRequest = ImageRequest.Builder(context = context).data(
            imageUri
        )
        listOfTransformations.add(MaskTransformation(context, R.drawable.ic_splash))
        listOfTransformations.add(
            ColorFilterTransformation(
                ColorUtils.setAlphaComponent(
                    colorValueTrans.value.hashCode(), 50
                )
            )
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.capturable(captureController)
            ) {
                AsyncImage(
                    model = imageRequest.transformations(listOfTransformations).build(),
                    contentDescription = "Image Description",
                    modifier = Modifier
//                        .clip(CircleShape)
                        .clip(RoundedCornerShape(20))
//                        .blur(20.dp)
                        .size(300.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    fallback = painterResource(id = R.drawable.ic_launcher_foreground),
                )
            }

            Button(
                onClick = {
                    scope.launch {
                        val bitmapAsync = captureController.captureAsync(Bitmap.Config.ARGB_8888)
                        try {
                            ssBitmap = bitmapAsync.await()
                        } catch (error: Throwable) {
                            Log.d("TAG", error.message.toString())
                        }
                    }
                },
            ) {
                Text(text = "Take Screenshot")
            }

            ssBitmap?.let { imageBitmap ->
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Screenshot"
                )
            }
        }

    }
}