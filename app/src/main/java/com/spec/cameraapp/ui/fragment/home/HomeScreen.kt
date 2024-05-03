package com.spec.cameraapp.ui.fragment.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.spec.cameraapp.R
import com.spec.cameraapp.db.table.Project
import com.spec.cameraapp.ui.components.LoadImageFromUri
import com.spec.cameraapp.viewmodels.MainViewModel

/*
*  In this Home Screen we can do below functionality
*   - Create new project
*   - List of saved projects
* */

@Composable
fun HomeScreen(mainViewModel: MainViewModel, onClickToCaptureImage: () -> Unit = {}) {


    mainViewModel.getAllProjects()
    val lazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(16.dp))
        ElevatedButton(onClick = onClickToCaptureImage) {
            Text(text = stringResource(id = R.string.lbl_create_new_project))
        }

        // ========== Fetch Project List from Database and show on List

        val projectList: List<Project> by mainViewModel.projectList.observeAsState(initial = listOf())
        if (projectList.isNotEmpty()) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 4.dp),
                state = lazyListState
            ) {
                items(items = projectList) { project ->
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
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
            }
        }
    }
}