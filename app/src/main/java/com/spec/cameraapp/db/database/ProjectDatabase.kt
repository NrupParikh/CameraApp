package com.spec.cameraapp.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spec.cameraapp.db.dao.ProjectDao
import com.spec.cameraapp.db.table.Project

@Database(entities = [(Project::class)], version = 1, exportSchema = false)
abstract class ProjectDatabase : RoomDatabase() {

    // DAO
    abstract fun projectDao(): ProjectDao
}