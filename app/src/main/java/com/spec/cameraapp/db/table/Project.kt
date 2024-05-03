package com.spec.cameraapp.db.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "project")
data class Project(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int? = 0,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "created_at")
    val createdAt: String
)