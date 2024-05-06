package com.spec.cameraapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spec.cameraapp.db.table.Project

@Dao
interface ProjectDao {

    // ========== CREATE NEW PROJECT ==========
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewProject(project: Project)

    // ========== GET ALL PROJECT LIST ==========
    @Query("SELECT * FROM project")
    suspend fun getAllProjects(): List<Project>

    // ========== DELETE PROJECT ==========
    @Delete
    suspend fun deleteProject(project: Project)

}