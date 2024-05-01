package com.spec.cameraapp.ui.fragment.capture_image

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.spec.cameraapp.ui.components.CircularImageButton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraPreviewScreen(onClickToCaptureImage: () -> Unit = {}) {

    // ================== TOGGLE CAMERA
    val toggleCamera = remember { mutableStateOf(false) }

    // ================== PICKER
    // content://media/picker/0/com.android.providers.media.photopicker/media/1000058380

    var photoUri: Uri? by remember { mutableStateOf(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }

    if (photoUri != null) {

        Log.d("TAG","GalleryImagePath ${photoUri.toString()}")
        Toast.makeText(LocalContext.current, photoUri.toString(), Toast.LENGTH_LONG).show()
        onClickToCaptureImage()
    }
    // =================== END OF PICKER


    // Change camera face Back or Front
    // val lensFacing = CameraSelector.LENS_FACING_BACK
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val preview = Preview.Builder().build()

    val previewView = remember {
        PreviewView(context)
    }

    val cameraxSelector = CameraSelector.Builder().requireLensFacing(
        if (toggleCamera.value) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        }
    ).build()

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    // ======================== LaunchedEffect

    // Changed from lensFacing to toggleCamera.value
    LaunchedEffect(toggleCamera.value) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview, imageCapture)
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // ======================== Bottom Button UI

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize()
    ) {

        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        BottomButtonUI(imageCapture, context, launcher, toggleCamera, onClickToCaptureImage)
    }


}

// ===================== STORE IMAGE TO INTERNAL STORAGE
private fun captureImage(
    imageCapture: ImageCapture,
    context: Context,
    onClickToCaptureImage: () -> Unit = {}
) {

    val name = "CameraxImage.jpeg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
    }
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()

    // ============================ SAVE IMAGE
    // content://media/external/images/media/1000058790

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val storedImagePath = outputFileResults.savedUri.toString()
                Log.d("TAG", "SUCCESS $storedImagePath")
                Toast.makeText(context, "Image stored at $storedImagePath", Toast.LENGTH_LONG)
                    .show()
                onClickToCaptureImage()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.d("TAG", "FAILED $exception")
                Toast.makeText(context, "Exception $exception", Toast.LENGTH_LONG)
                    .show()
            }

        })
}

// ===================== GETTING CAMERA PROVIDER

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.run { get() })
            }, ContextCompat.getMainExecutor(this))
        }
    }

@Composable
fun BottomButtonUI(
    imageCapture: ImageCapture,
    context: Context,
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    toggleCamera: MutableState<Boolean>,
    onClickToCaptureImage: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(Color.Black.copy(alpha = 0.08f))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            CircularImageButton(
                onClick = {
                    launcher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                fillColor = Color.Black,
                modifier = Modifier.size(64.dp),
                hasIcon = false
            )

            CircularImageButton(
                onClick = { captureImage(imageCapture, context, onClickToCaptureImage) },
                fillColor = Color.White,
                modifier = Modifier.size(72.dp),
                hasIcon = false
            )

            CircularImageButton(
                onClick = {
                    toggleCamera.value = !toggleCamera.value
                },
                fillColor = Color.Black,
                modifier = Modifier.size(64.dp),
                hasIcon = true
            )
        }
    }
}