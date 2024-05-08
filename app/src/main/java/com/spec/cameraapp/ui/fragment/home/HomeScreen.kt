package com.spec.cameraapp.ui.fragment.home

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.transform.CircleCropTransformation
import com.spec.cameraapp.R
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.ui.components.SelectImagePopUp
import com.spec.cameraapp.ui.fragment.capture_image.storeImagePathInDB
import com.spec.cameraapp.ui.navigation.Route
import com.spec.cameraapp.viewmodels.MainViewModel

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
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }
    if (photoUri != null) {

        Log.d("TAG", "GalleryImagePath111 ${photoUri.toString()}")
        storeImagePathInDB(mainViewModel, photoUri.toString())
        Toast.makeText(LocalContext.current, photoUri.toString(), Toast.LENGTH_LONG).show()
        mainViewModel.imageUrl.value = photoUri.toString()
        navController.navigate(route = Route.EditImage.route)
        photoUri=null

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

        val projectListDecending: List<Project> by mainViewModel.projectList.observeAsState(initial = listOf())
        val projectList=projectListDecending.reversed()
        if (projectList.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 4.dp),
                state = lazyListState
            ) {
                items(items = projectList) { project ->
                    ProjectItem(project = project, onItemClick = { selectedProject ->
                        Log.d("TAG", "Selected ImageURL " + selectedProject.imageUrl)
                        mainViewModel.imageUrl.value = selectedProject.imageUrl
                        navController.navigate(route = Route.EditImage.route)
                    })
                }
            }
        }
    }
}



@Composable
fun ProjectItem(project: Project, onItemClick: (Project) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                onItemClick.invoke(project)
            }),
        shape = RoundedCornerShape(10.dp),
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
                transformation = CircleCropTransformation(),
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
    }
}