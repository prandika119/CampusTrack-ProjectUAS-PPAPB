package com.example.campustrack.model.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.campustrack.model.schedule.Subject

@Entity("task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val deadline: String,
    var status: Boolean = false,
)
