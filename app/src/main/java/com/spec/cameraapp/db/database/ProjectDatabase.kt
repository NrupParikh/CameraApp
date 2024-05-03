package com.spec.cameraapp.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spec.cameraapp.db.dao.ProjectDao
import com.spec.cameraapp.db.table.Project

@Database(entities = [(Project::class)], version = 1, exportSchema = false)
abstract class ProjectDatabase : RoomDatabase() {

    // DAO
    abstract fun projectDao(): ProjectDao

//    companion object {
//
//        @Volatile
//        private var INSTANCE: ProjectDatabase? = null
//
//        /*
//            The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
//            This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
//            It means that changes made by one thread to INSTANCE are visible to all other threads immediately.
//        */
//
//        fun getInstance(context: Context): ProjectDatabase {
//
//            // only one thread of execution at a time can enter this block of code [synchronized]
//
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        ProjectDatabase::class.java,
//                        DATABASE_NAME
//                    ).fallbackToDestructiveMigration().build()
//
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
}