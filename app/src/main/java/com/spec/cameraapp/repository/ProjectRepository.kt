package com.spec.cameraapp.repository

import com.spec.cameraapp.db.dao.ProjectDao
import com.spec.cameraapp.db.table.Project
import javax.inject.Inject

class ProjectRepository @Inject constructor(private val projectDao: ProjectDao) {

    suspend fun getAllProjects(): List<Project> {
        return projectDao.getAllProjects()
    }

    suspend fun createNewProject(project: Project) {
        return projectDao.createNewProject(project)
    }

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }
}