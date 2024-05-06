package com.spec.cameraapp.di

import android.content.Context
import androidx.room.Room
import com.spec.cameraapp.db.dao.ProjectDao
import com.spec.cameraapp.db.database.ProjectDatabase
import com.spec.cameraapp.repository.ProjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProjectDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProjectDatabase::class.java,
            "appDB"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideProjectDao(appDatabase: ProjectDatabase): ProjectDao {
        return appDatabase.projectDao()
    }

    @Provides
    @Singleton

    fun provideProjectRepository(projectDao: ProjectDao): ProjectRepository {
        return ProjectRepository(projectDao)
    }
}