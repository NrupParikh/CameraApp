package com.spec.cameraapp.ui.fragment.home

import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.transform.CircleCropTransformation
import com.spec.cameraapp.R
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.ui.components.SelectImagePopUp
import com.spec.cameraapp.ui.fragment.capture_image.storeImagePathInDB
import com.spec.cameraapp.ui.navigation.Route
import com.spec.cameraapp.ui.theme.CameraAppTheme
import com.spec.cameraapp.viewmodels.MainViewModel
import kotlinx.coroutines.delay

/*
*  In this Home Screen we can do below functionality
*   - Create new project
*   - List of saved projects
* */

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController
) {

    mainViewModel.getAllProjects()
    val lazyListState = rememberLazyListState()

    var showDialog by remember { mutableStateOf(false) }
    var photoUri: Uri? by remember { mutableStateOf(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia())
        { uri ->
            photoUri = uri
        }
    if (photoUri != null) {

        val path =mainViewModel.setTempPath(photoUri!!, LocalContext.current)

        storeImagePathInDB(mainViewModel, path)
        Log.d("JBK", "HomeScreen: URL:${path}")
        Toast.makeText(LocalContext.current, photoUri.toString(), Toast.LENGTH_LONG).show()
        mainViewModel.imageUrl.value = photoUri.toString()
        navController.navigate(route = Route.EditImage.route)
        photoUri = null

    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(16.dp))
        ElevatedButton(onClick = { showDialog = true }) {
            Text(text = stringResource(id = R.string.lbl_create_new_project))
        }

        // ============= POPUP TO SELECT IMAGE FROM GALLERY OR CAPTURE FROM CAMERA
        SelectImagePopUp(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onClickCamera = {
                navController.navigate(route = Route.CaptureImage.route)
            },
            onClickGallery = {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
        )

        // ========== Fetch Project List from Database and show on List

        val projectListDescending: List<Project> by mainViewModel.projectList.observeAsState(initial = listOf())
        val projectList = projectListDescending.reversed()
        if (projectList.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 4.dp),
                state = lazyListState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)


            ) {
                items(items = projectList) { project ->
                    ProjectItem(
                        project = project,
                        onItemClick = { selectedProject ->
                        mainViewModel.imageUrl.value = selectedProject.imageUrl
                        navController.navigate(route = Route.EditImage.route)
                    },/*
                 onEdit = {
                         selectedProject ->
                     mainViewModel.imageUrl.value = selectedProject.imageUrl
                     navController.navigate(route = Route.EditImage.route)
                     Log.d("JBK", "on Edit methode call on swap")

                 },*/
                 onRemove = { it ->
                     Log.d("JBK", "on delete remove  methode call on swap")

                                    mainViewModel.deleteItem(it)

                 })
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectItem(project: Project,
                onItemClick: (Project) -> Unit,
                onRemove: (Project) -> Unit,
                ) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(project)
    val dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd)
            {
                show = false
                true
            } else false
        }, positionalThreshold = { 150.dp.toPx() }
    )
    Box(
        modifier = Modifier.padding(0.dp)
    ) {
        AnimatedVisibility(
            show, exit = fadeOut(spring())
        ) {
            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier,
                background = {
                    DismissBackground(dismissState)
                },
                dismissContent = {
                    Card(
                        modifier = Modifier
                            .padding(0.dp)
                            .fillMaxWidth()
                            .clickable(onClick = {
                                onItemClick.invoke(project)
                            }),
                        shape = RoundedCornerShape(9.dp),
                        border = BorderStroke(0.1.dp,
                            MaterialTheme.colorScheme.primary)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // ==================== LOAD IMAGE USING COIL

                            LoadImageFromUri(
                                context = LocalContext.current,
                                imageUri = project.imageUrl,
                                transformation = listOf(CircleCropTransformation()),
                                imageSize = 84.dp,
                                scaleType = ContentScale.Crop
                            )

                            // ========== Content

                            Column {
                                Text(
                                    text = String.format(
                                        stringResource(id = R.string.lbl_project_id),
                                        project.id
                                    ),
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = String.format(
                                        stringResource(id = R.string.lbl_created_at),
                                        project.createdAt
                                    ),
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            )
        }
    }

    LaunchedEffect(show) {
        if (!show) {
            delay(800)
            onRemove(currentItem)
            //onEdit(currentItem)
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show()
        }
    }
   /* Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                onItemClick.invoke(project)
            }),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ==================== LOAD IMAGE USING COIL

            LoadImageFromUri(
                context = LocalContext.current,
                imageUri = project.imageUrl,
                transformation = listOf(CircleCropTransformation()),
                imageSize = 64.dp,
                scaleType = ContentScale.Crop
            )

            // ========== Content

            Column {
                Text(
                    text = String.format(
                        stringResource(id = R.string.lbl_project_id),
                        project.id
                    ),
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = String.format(
                        stringResource(id = R.string.lbl_created_at),
                        project.createdAt
                    ),
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }*/
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: DismissState) {
    val color = when (dismissState.dismissDirection) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFFFF1744)
        null -> Color.Transparent
    }
    val direction = dismissState.dismissDirection

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(0.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) Icon(
            Icons.Default.Delete,
            // make sure add baseline_archive_24 resource to drawable folder
           // painter = painterResource(R.drawable.edit),
            contentDescription = "delete"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewProjectItemNight() {
    CameraAppTheme {
        ProjectItem(
            project = Project(
                id = 1,
                imageUrl = "",
                createdAt = "2024-05-09T12:58:37.133549z",
            ),
            onItemClick = {},
            onRemove = {}

        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewProjectItemLight() {
    CameraAppTheme {
        ProjectItem(
            project = Project(
                id = 1,
                imageUrl = "",
                createdAt = "2024-05-09T12:58:37.133549z"
            ),
            onItemClick = {},
            onRemove = {}
        )
    }
}