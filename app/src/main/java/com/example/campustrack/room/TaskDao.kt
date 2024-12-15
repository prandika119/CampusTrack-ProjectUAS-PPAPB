package com.example.campustrack.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.campustrack.model.task.Task

@Dao
interface TaskDao {
    @Insert
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task WHERE status = 0 AND deadline > datetime('now') ORDER BY deadline ")
    suspend fun getUnCheckTask(): List<Task>

    @Query("SELECT * FROM task where status=1 ORDER BY deadline ")
    suspend fun getCheckTask(): List<Task>

    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTask(id: Int): Task

    @Query("SELECT * FROM task WHERE deadline BETWEEN date('now') AND date('now', '+1 day')")
    suspend fun getTasksDueTodayOrTomorrow(): List<Task>
}