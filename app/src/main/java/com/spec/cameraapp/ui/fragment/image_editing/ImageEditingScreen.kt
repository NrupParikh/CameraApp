package com.spec.cameraapp.ui.fragment.image_editing

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import coil.transform.Transformation
import com.spec.cameraapp.R
import com.spec.cameraapp.db.dao.ProjectDao
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.repository.ProjectRepository
import com.spec.cameraapp.ui.components.ColorPic
import com.spec.cameraapp.ui.components.ImageTransformationItem
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.ui.components.MaskPic
import com.spec.cameraapp.ui.components.PreviewDialog
import com.spec.cameraapp.ui.components.screenshots.capturable
import com.spec.cameraapp.ui.components.screenshots.rememberCaptureController
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.utils.transformationList
import com.spec.cameraapp.utils.transformations.ColorFilterTransformation
import com.spec.cameraapp.utils.transformations.GrayscaleTransformation
import com.spec.cameraapp.utils.transformations.MaskTransformation
import com.spec.cameraapp.utils.transformations.TransformationType
import com.spec.cameraapp.viewmodels.MainViewModel
import kotlinx.coroutines.launch

/*
*  In this Image Editing Screen we can do below functionality
*   - Display Captured Image or selected image
*   - Perform filtering
//    val photoUri = "content://media/external/images/media/1000060027"
//    val photoUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000058380"
* */

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeApi::class)
@Composable
fun ImageEditingScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {

    val photoUri = mainViewModel.imageUrl.observeAsState(initial = "")
    val selectedTransformation = mainViewModel.selectedTransformation.observeAsState()
    val listOfTransformations = remember { mutableListOf<Transformation>() }

    // ============ ROUNDED_CORNER
    val roundCornerSliderValueTrans = mainViewModel.roundCornerSliderValueTrans.observeAsState()
    val roundCornerSliderValueTransIsSelected =
        mainViewModel.roundCornerSliderValueTransIsSelected.observeAsState()

    // ============ CIRCLE_CROP
    val circleCropSliderValueTrans = mainViewModel.circleCropSliderValueTrans.observeAsState()
    val circleCropSliderValueTransIsSelected =
        mainViewModel.circleCropSliderValueTransIsSelected.observeAsState()

    // ============ RESIZE_IMAGE
    val resizeSliderValueTrans = mainViewModel.resizeSliderValueTrans.observeAsState()
    val resizeSliderValueTransIsSelected =
        mainViewModel.resizeSliderValueTransIsSelected.observeAsState()

    // ============ BLUR
    val blurSliderValueTrans = mainViewModel.blurSliderValueTrans.observeAsState()
    val blurSliderValueTransIsSelected =
        mainViewModel.blurSliderValueTransIsSelected.observeAsState()

    // ============ COLOR_FILTER
    val colorValueTransIsSelected =
        mainViewModel.colorValueTransIsSelected.observeAsState()
    val colorValueTrans = mainViewModel.colorValueTrans.observeAsState()

    // ============ MASK
    val maskValueTransIsSelected =
        mainViewModel.maskValueTransIsSelected.observeAsState()
    val selectedMaskType = mainViewModel.selectedMaskType.observeAsState()

    // ========================= CAPTURE SS OF COMPOSABLE (Save Image)

    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()
    var ssBitmap: ImageBitmap? by remember { mutableStateOf(null) }


    var showPreviewDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // ========================= GETTING BITMAP OF OUR TRANSFORMED IMAGE COMPOSABLE COMPONENT
        Button(onClick = {
            scope.launch {
                val bitmapAsync = captureController.captureAsync(Bitmap.Config.ARGB_8888)
                try {
                    ssBitmap = bitmapAsync.await()
                    showPreviewDialog = true
                } catch (error: Throwable) {
                    Log.d("TAG", error.message.toString())
                }
            }
        }) {
            Text(text = stringResource(id = R.string.lbl_preview))
        }

//        ssBitmap?.let { imageBitmap ->
//            Image(
//                bitmap = imageBitmap,
//                contentDescription = "Screenshot"
//            )
//            mainViewModel.shareImageViaIntent(LocalContext.current,imageBitmap, MIME_TYPE_IMAGE)
//            showPreviewDialog = true
//
//        }

        // ========================= SHOWING TRANSFORMED IMAGE IN PREVIEW DIALOG TO SHARE

        ssBitmap?.let {
            PreviewDialog(
                imageBitmap = it,
                showDialog = showPreviewDialog,
                onDismiss = { showPreviewDialog = false },
                onPositiveButtonClick = {
                    // ToDo Open Share Intent
                },
            )
        }


        // ==================================================


        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                var transformation: Transformation? = null
                val transformationValue = selectedTransformation.value
                when (transformationValue) {
                    TransformationType.ROUNDED_CORNER.name -> transformation = null
                    TransformationType.CIRCLE_CROP.name -> transformation = null
                    TransformationType.RESIZE_IMAGE.name -> transformation = null
                    TransformationType.BLUR.name -> transformation = null
                    TransformationType.GRAY_SCALE.name -> transformation =
                        GrayscaleTransformation()

                    TransformationType.COLOR_FILTER.name -> {
                        transformation =
                            ColorFilterTransformation(
                                ColorUtils.setAlphaComponent(
                                    colorValueTrans.value.hashCode(),
                                    80
                                )
                            )
                        // Log.d("JBK", "color value${colorValueTrans.value}")
                    }

                    TransformationType.MASK.name -> {
                        Log.d("TAG", "MASK_TYPE ${selectedMaskType.value}")

                        val type = selectedMaskType.value ?: ""

                        // ===================== SET VECTOR IMAGE BASED ON SELECTED MASK ITEM
                        when (type) {
                            TransformationType.ROUNDED_CORNER.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_rounded_corner
                                )
                            }

                            TransformationType.CIRCLE_CROP.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_circle_crop
                                )
                            }

                            TransformationType.RESIZE_IMAGE.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_resize
                                )
                            }

                            TransformationType.BLUR.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_blur
                                )
                            }

                            TransformationType.GRAY_SCALE.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_gray_scale
                                )
                            }

                            TransformationType.COLOR_FILTER.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_color_filter
                                )
                            }

                            TransformationType.MASK.toString() -> {
                                transformation = MaskTransformation(
                                    context = LocalContext.current,
                                    R.drawable.ic_mask
                                )
                            }
                        }
                    }
                }

                // Adding multiple transformations
                transformation?.let { it ->

                    listOfTransformations.add(it)

                    /* if(listOfTransformations.contains(it))
                        listOfTransformations.remove(it)
                    else
                        listOfTransformations.add(it)*/
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.capturable(captureController)
                ) {
                    LoadImageFromUri(
                        context = LocalContext.current,
                        imageUri = photoUri.value,
                        transformation = listOfTransformations,
                        imageSize =
                        if (circleCropSliderValueTransIsSelected.value == true) {
                            circleCropSliderValueTrans.value?.dp ?: 350.dp
                        } else if (resizeSliderValueTransIsSelected.value == true) {
                            resizeSliderValueTrans.value?.dp ?: 350.dp
                        } else {
                            350.dp
                        },
                        scaleType = ContentScale.Crop,
                        roundedCornerRadius = roundCornerSliderValueTrans.value?.dp ?: 0.dp,
                        blurRadius = blurSliderValueTrans.value?.dp ?: 0.dp,
                        isCircleShape = circleCropSliderValueTransIsSelected.value == true
                    )
                }

            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (roundCornerSliderValueTransIsSelected.value!!) {
                Slider(
                    value = roundCornerSliderValueTrans.value!!,
                    onValueChange = { mainViewModel.roundCornerSliderValueTrans.value = it },
                    valueRange = 1f..100f
                )
            } else if (circleCropSliderValueTransIsSelected.value!!) {
                Slider(
                    value = circleCropSliderValueTrans.value!!,
                    onValueChange = { mainViewModel.circleCropSliderValueTrans.value = it },
                    valueRange = 1f..350f
                )
            } else if (blurSliderValueTransIsSelected.value!!) {
                Slider(
                    value = blurSliderValueTrans.value!!,
                    onValueChange = { mainViewModel.blurSliderValueTrans.value = it },
                    valueRange = 1f..100f
                )
            } else if (resizeSliderValueTransIsSelected.value!!) {
                Slider(
                    value = resizeSliderValueTrans.value!!,
                    onValueChange = { mainViewModel.resizeSliderValueTrans.value = it },
                    valueRange = 1f..350f
                )
            } else if (maskValueTransIsSelected.value!!) {
                MaskPic(mainViewModel)

            } else if (colorValueTransIsSelected.value!!) {
                ColorPic(mainViewModel)
            }
        }
        TransformationBox(mainViewModel)
    }
}

// ========================= BOTTOM OPTIONS FOR TRANSFORMATIONS

@SuppressLint("LogNotTimber")
@Composable
fun TransformationBox(mainViewModel: MainViewModel) {
    val lazyListState = rememberLazyListState()

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        state = lazyListState
    ) {
        items(items = transformationList)
        { transformation ->
            ImageTransformationItem(
                transformation,
                false,
                onItemClick = { selectedTransformation, isClick ->
                    mainViewModel.selectedTransformation.value = selectedTransformation.type
                    when (selectedTransformation.type) {

                        // ============ ROUNDED_CORNER
                        TransformationType.ROUNDED_CORNER.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = isClick
                                circleCropSliderValueTransIsSelected.value = false
                                resizeSliderValueTransIsSelected.value = false
                                blurSliderValueTransIsSelected.value = false
                                colorValueTransIsSelected.value = false
                                maskValueTransIsSelected.value = false
                            }
                        }

                        // ============ CIRCLE_CROP
                        TransformationType.CIRCLE_CROP.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = false
                                circleCropSliderValueTransIsSelected.value = isClick
                                resizeSliderValueTransIsSelected.value = false
                                blurSliderValueTransIsSelected.value = false
                                colorValueTransIsSelected.value = false
                                maskValueTransIsSelected.value = false
                            }
                        }

                        // ============ RESIZE_IMAGE
                        TransformationType.RESIZE_IMAGE.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = false
                                circleCropSliderValueTransIsSelected.value = false
                                resizeSliderValueTransIsSelected.value = isClick
                                blurSliderValueTransIsSelected.value = false
                                colorValueTransIsSelected.value = false
                                maskValueTransIsSelected.value = false
                            }
                        }

                        // ============ BLUR
                        TransformationType.BLUR.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = false
                                circleCropSliderValueTransIsSelected.value = false
                                resizeSliderValueTransIsSelected.value = false
                                blurSliderValueTransIsSelected.value = isClick
                                colorValueTransIsSelected.value = false
                                maskValueTransIsSelected.value = false
                            }
                        }

                        // ============ GRAY_SCALE
                        TransformationType.GRAY_SCALE.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = false
                                circleCropSliderValueTransIsSelected.value = false
                                resizeSliderValueTransIsSelected.value = false
                                blurSliderValueTransIsSelected.value = false
                                colorValueTransIsSelected.value = false
                                maskValueTransIsSelected.value = false
                            }
                        }

                        // ============ COLOR_FILTER
                        TransformationType.COLOR_FILTER.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = false
                                circleCropSliderValueTransIsSelected.value = false
                                resizeSliderValueTransIsSelected.value = false
                                blurSliderValueTransIsSelected.value = false
                                colorValueTransIsSelected.value = isClick
                                maskValueTransIsSelected.value = false
                            }
                        }

                        // ============ MASK
                        TransformationType.MASK.name -> {
                            mainViewModel.apply {
                                roundCornerSliderValueTransIsSelected.value = false
                                circleCropSliderValueTransIsSelected.value = false
                                resizeSliderValueTransIsSelected.value = false
                                blurSliderValueTransIsSelected.value = false
                                colorValueTransIsSelected.value = false
                                maskValueTransIsSelected.value = isClick
                            }
                        }
                    }
                })
        }
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewTransformationBoxNight() {
    CameraAppTheme {
        TransformationBox(MainViewModel(ProjectRepository(projectDao = ProjectDaoClass())))
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun PreviewTransformationBoxLight() {
    CameraAppTheme {
        TransformationBox(MainViewModel(ProjectRepository(projectDao = ProjectDaoClass())))
    }
}

// Preview Only Impl class
class ProjectDaoClass : ProjectDao {
    override suspend fun createNewProject(project: Project) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProjects(): List<Project> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProject(project: Project) {
        TODO("Not yet implemented")
    }

}

/*
*    // transformation = RoundedCornersTransformation(50f),
        // transformation = CircleCropTransformation(), // Also adjust the imageSize
        // transformation = SquareCropTransformation(),
        // transformation = BlurTransformation(context = LocalContext.current, 20f),
        // transformation = GrayscaleTransformation(),
        // transformation = ColorFilterTransformation(ColorUtils.setAlphaComponent(Color.GREEN,50)),
        // transformation = MaskTransformation(context = LocalContext.current,R.drawable.ic_splash)
* */