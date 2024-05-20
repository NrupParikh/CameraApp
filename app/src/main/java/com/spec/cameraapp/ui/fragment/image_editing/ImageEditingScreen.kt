package com.spec.cameraapp.ui.fragment.image_editing

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.graphics.ColorUtils
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.core.graphics.component4
import androidx.core.graphics.red
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import com.spec.cameraapp.R
import com.spec.cameraapp.db.dao.ProjectDao
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.repository.ProjectRepository
import com.spec.cameraapp.ui.components.ImageTransformationItem
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.utils.transformations.BlurTransformation
import com.spec.cameraapp.utils.transformations.ColorFilterTransformation
import com.spec.cameraapp.utils.transformations.GrayscaleTransformation
import com.spec.cameraapp.utils.transformations.MaskTransformation
import com.spec.cameraapp.utils.transformations.SquareCropTransformation
import com.spec.cameraapp.utils.transformations.TransformationItem
import com.spec.cameraapp.utils.transformations.TransformationType
import com.spec.cameraapp.viewmodels.MainViewModel
import io.mhssn.colorpicker.ColorPicker
import io.mhssn.colorpicker.ColorPickerDialog
import io.mhssn.colorpicker.ColorPickerType
import io.mhssn.colorpicker.ext.toHex
import io.mhssn.colorpicker.ext.transparentBackground

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
    var roundCornerSliderValueTrans = mainViewModel.roundCornerSliderValueTrans.observeAsState()
    var roundCornerSliderValueTransisSelected =  mainViewModel.roundCornerSliderValueTransIsSelected.observeAsState()


    val blurSliderValueTrans = mainViewModel.blurSliderValueTrans.observeAsState()
    val blurSliderValueTransIsSelected =
        mainViewModel.blurSliderValueTransIsSelected.observeAsState()

    val circleCropSliderValueTrans = mainViewModel.circleCropSliderValueTrans.observeAsState()
    val circleCropSliderValueTransIsSelected =
        mainViewModel.circleCropSliderValueTransIsSelected.observeAsState()

    val resizeSliderValueTrans = mainViewModel.resizeSliderValueTrans.observeAsState()
    val resizeSliderValueTransIsSelected =
        mainViewModel.resizeSliderValueTransIsSelected.observeAsState()

    val colorValueTransIsSelected =
        mainViewModel.colorValueTransIsSelected.observeAsState()
    val colorValueTrans = mainViewModel.colorValueTrans.observeAsState()

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
                    TransformationType.ROUNDED_CORNER.name -> transformation = null
//                        RoundedCornersTransformation(roundCornerSliderValueTrans.value!!)

                    TransformationType.CIRCLE_CROP.name -> transformation = null
//                        CircleCropTransformation()

                    TransformationType.RESIZE_IMAGE.name -> transformation = null
//                        SquareCropTransformation()

                    TransformationType.BLUR.name -> transformation = null
//                        BlurTransformation(
//                            context = LocalContext.current,
//                            blurSliderValueTrans.value!!
//                        )


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

                    TransformationType.MASK.name -> transformation =
                        MaskTransformation(context = LocalContext.current, R.drawable.ic_splash)
                }

                // Adding multiple transformations
                transformation?.let { it ->

                    listOfTransformations.add(it)

                    /* if(listOfTransformations.contains(it))
                        listOfTransformations.remove(it)
                    else
                        listOfTransformations.add(it)*/
                }

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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (roundCornerSliderValueTransisSelected.value!!) {
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
            }
            else if (colorValueTransIsSelected.value!!) {
                ColorPic(mainViewModel)
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
            id = 1,
            icon = R.drawable.ic_rounded_corner,
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
            TransformationType.RESIZE_IMAGE.name
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
        items(items = transformationList)
        { transformation ->
            ImageTransformationItem(
                transformation,
                false,
                onItemClick = { selectedTransformation, isClick ->
                    mainViewModel.selectedTransformation.value = selectedTransformation.type
                    when (selectedTransformation.type) {
                        TransformationType.ROUNDED_CORNER.name -> {

                            mainViewModel.roundCornerSliderValueTransIsSelected.value = isClick
                            mainViewModel.blurSliderValueTransIsSelected.value = false
                            mainViewModel.circleCropSliderValueTransIsSelected.value = false
                            mainViewModel.resizeSliderValueTransIsSelected.value = false

                        }

                        TransformationType.CIRCLE_CROP.name -> {
                            mainViewModel.circleCropSliderValueTransIsSelected.value = isClick
                            mainViewModel.blurSliderValueTransIsSelected.value = false
                            mainViewModel.roundCornerSliderValueTransIsSelected.value = false
                            mainViewModel.resizeSliderValueTransIsSelected.value = false
                        }

                        TransformationType.RESIZE_IMAGE.name -> {
                            mainViewModel.resizeSliderValueTransIsSelected.value = isClick
                            mainViewModel.circleCropSliderValueTransIsSelected.value = false
                            mainViewModel.blurSliderValueTransIsSelected.value = false
                            mainViewModel.roundCornerSliderValueTransIsSelected.value = false
                        }

                        TransformationType.BLUR.name -> {
                            mainViewModel.blurSliderValueTransIsSelected.value = isClick
                            mainViewModel.roundCornerSliderValueTransIsSelected.value = false
                            mainViewModel.circleCropSliderValueTransIsSelected.value = false
                            mainViewModel.resizeSliderValueTransIsSelected.value = false

                        }

                        TransformationType.GRAY_SCALE.name -> {

                        }

                        TransformationType.COLOR_FILTER.name -> {
                            mainViewModel.colorValueTransIsSelected.value = isClick

                        }

                        TransformationType.MASK.name -> {

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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColorPic(mainViewModel: MainViewModel) {
    val colorValueTransIsSelected =
        mainViewModel.colorValueTransIsSelected.observeAsState()
    val colorValueTrans = mainViewModel.colorValueTrans.observeAsState()
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var color by remember { mutableStateOf(colorValueTrans.value) }
        var colorPickerType by remember {
            mutableStateOf<ColorPickerType>(ColorPickerType.Ring())
        }
        var showDialog by remember {
            mutableStateOf(false)
        }
        ColorPickerDialog(
            show = showDialog,
            type = colorPickerType,
            properties = DialogProperties(),
            onDismissRequest = {
                showDialog = false
            },

            onPickedColor = {
                showDialog = false
                mainViewModel.colorValueTrans.value=it
                Log.d("JBK", "ColorPic: ${it.toHex()}")
                Log.d("JBK", "ColorPic value: ${it.value}")
                Log.d("JBK", "ColorPic  int: ${it.value.toInt()}")
            },
        )
       /* ColorPicker(type = colorPickerType) {
            color = it.value.toInt()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            val (alpha, red, green, blue) = color
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Hex")
                Text(text = "#${color}")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Alpha")
                Text(text = alpha.toString())
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Red")
                Text(text = red.toString())
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Green")
                Text(text = green.toString())
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Blue")
                Text(text = blue.toString())
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .size(80.dp, 50.dp)
                .clip(RoundedCornerShape(50))
                .border(
                    0.3.dp,
                    SolidColor(androidx.compose.ui.graphics.Color.Red),
                    RoundedCornerShape(50)
                )
                .transparentBackground(verticalBoxesAmount = 8)
                .background(
                    SolidColor(androidx.compose.ui.graphics.Color.Red),
                    RoundedCornerShape(50)
                )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Color Picker Type",
            )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(2),
            content = {
                item {
                    OutlinedButton(onClick = {
                        colorPickerType = ColorPickerType.Classic()
                    }, shape = RoundedCornerShape(50)) {
                        Text(text = "Classic")
                    }
                }
                item {
                    OutlinedButton(onClick = {
                        colorPickerType = ColorPickerType.Circle()
                    }, shape = RoundedCornerShape(50)) {
                        Text(text = "Circle")
                    }
                }
                item {
                    OutlinedButton(onClick = {
                        colorPickerType = ColorPickerType.Ring()
                    }, shape = RoundedCornerShape(50)) {
                        Text(text = "Ring")
                    }
                }
                item {
                    OutlinedButton(onClick = {
                        colorPickerType = ColorPickerType.SimpleRing()
                    }, shape = RoundedCornerShape(50)) {
                        Text(text = "Simple Ring")
                    }
                }
            })*/
        OutlinedButton(onClick = {
            showDialog = true
        }, shape = RoundedCornerShape(50)) {
            Text(text = "Select Color")
        }
    }
}

