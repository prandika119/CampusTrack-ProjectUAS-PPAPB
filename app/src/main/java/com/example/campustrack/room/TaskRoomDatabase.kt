package com.example.campustrack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.campustrack.model.task.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskRoomDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

//    companion object {
//        @Volatile
//        private var INSTANCE: TaskRoomDatabase? = null
//
//        fun getDatabase(context: Context): TaskRoomDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    TaskRoomDatabase::class.java,
//                    "doa_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
    companion object {

        @Volatile private var instance : TaskRoomDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TaskRoomDatabase::class.java,
            "taskschedule.db"
        ).build()

    }
}