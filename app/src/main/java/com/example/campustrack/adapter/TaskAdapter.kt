package com.example.campustrack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.campustrack.TaskFragmentDirections
import com.example.campustrack.databinding.ItemTaskBinding
import com.example.campustrack.model.task.Task
import com.example.campustrack.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdapter (private var listTask: List<Task>, private val db: TaskRoomDatabase): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(listTask[position])
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                cbSubject.setOnCheckedChangeListener { _, isChecked ->
                    task.status = isChecked
                    CoroutineScope(Dispatchers.IO).launch {
                        db.taskDao().updateTask(task)
                    }
                }
                tvTitle.text = task.title
                tvTime.text = task.deadline
                if (task.status) {
                    cbSubject.isChecked = true
                }
                cardTask.setOnClickListener {
                    val action = TaskFragmentDirections.actionTaskFragmentToDetailTaskFragment(task.id)
                    it.findNavController().navigate(action)

                }
            }
        }
    }
}