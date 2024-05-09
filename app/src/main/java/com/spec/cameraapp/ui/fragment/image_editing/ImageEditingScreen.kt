package com.spec.cameraapp.ui.fragment.image_editing

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import androidx.navigation.NavHostController
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.spec.cameraapp.R
import com.spec.cameraapp.ui.components.ImageTransformationItem
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.utils.transformations.BlurTransformation
import com.spec.cameraapp.utils.transformations.ColorFilterTransformation
import com.spec.cameraapp.utils.transformations.GrayscaleTransformation
import com.spec.cameraapp.utils.transformations.MaskTransformation
import com.spec.cameraapp.utils.transformations.SquareCropTransformation
import com.spec.cameraapp.utils.transformations.TransformationItem
import com.spec.cameraapp.utils.transformations.TransformationType
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
    val selectedTransformation = mainViewModel.selectedTransformation.observeAsState()
    val listOfTransformations = remember { mutableListOf<Transformation>() }

//    val photoUri = "content://media/external/images/media/1000060027"
//    val photoUri = "content://media/picker/0/com.android.providers.media.photopicker/media/1000058380"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // transformation = RoundedCornersTransformation(50f),
        // transformation = CircleCropTransformation(), // Also adjust the imageSize
        // transformation = SquareCropTransformation(),
        // transformation = BlurTransformation(context = LocalContext.current, 20f),
        // transformation = GrayscaleTransformation(),
        // transformation = ColorFilterTransformation(ColorUtils.setAlphaComponent(Color.GREEN,50)),
        // transformation = MaskTransformation(context = LocalContext.current,R.drawable.ic_splash)

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
                    TransformationType.ROUNDED_CORNER.name -> transformation =
                        RoundedCornersTransformation(50f)

                    TransformationType.CIRCLE_CROP.name -> transformation =
                        CircleCropTransformation()

                    TransformationType.SQUARE_CROP.name -> transformation =
                        SquareCropTransformation()

                    TransformationType.BLUR.name -> transformation =
                        BlurTransformation(context = LocalContext.current, 20f)


                    TransformationType.GRAY_SCALE.name -> transformation =
                        GrayscaleTransformation()

                    TransformationType.COLOR_FILTER.name -> transformation =
                        ColorFilterTransformation(
                            ColorUtils.setAlphaComponent(Color.MAGENTA, 80)
                        )

                    TransformationType.MASK.name -> transformation =
                        MaskTransformation(context = LocalContext.current, R.drawable.ic_splash)
                }

                // Adding multiple transformations
                transformation?.let { listOfTransformations.add(it) }

                LoadImageFromUri(
                    context = LocalContext.current,
                    imageUri = photoUri.value,
                    transformation = listOfTransformations,
                    imageSize = 500.dp,
                    scaleType = ContentScale.Crop
                )
            }
        }

        TransformationBox(mainViewModel)
    }
}


@SuppressLint("LogNotTimber")
@Composable
fun TransformationBox(mainViewModel: MainViewModel) {
    val lazyListState = rememberLazyListState()

    // List of Transformations
    val transformationList: List<TransformationItem> = mutableListOf(
        TransformationItem(
            id = 1, icon = R.drawable.ic_rounded_corner,
            TransformationType.ROUNDED_CORNER.name
        ),
        TransformationItem(
            id = 2,
            icon = R.drawable.ic_circle_crop,
            TransformationType.CIRCLE_CROP.name
        ),
        TransformationItem(
            id = 3,
            icon = R.drawable.ic_sqaure_crop,
            TransformationType.SQUARE_CROP.name
        ),
        TransformationItem(
            id = 4,
            icon = R.drawable.ic_blur,
            TransformationType.BLUR.name
        ),
        TransformationItem(
            id = 5,
            icon = R.drawable.ic_gray_scale,
            TransformationType.GRAY_SCALE.name
        ),
        TransformationItem(
            id = 6,
            icon = R.drawable.ic_color_filter,
            TransformationType.COLOR_FILTER.name
        ),
        TransformationItem(
            id = 7,
            icon = R.drawable.ic_mask,
            TransformationType.MASK.name
        ),
    )

    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        state = lazyListState
    ) {
        items(items = transformationList) { transformation ->
            ImageTransformationItem(transformation, onItemClick = { selectedTransformation ->
                mainViewModel.selectedTransformation.value = selectedTransformation.type
            })
        }
    }
}