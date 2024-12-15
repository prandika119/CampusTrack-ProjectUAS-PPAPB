package com.example.campustrack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.campustrack.adapter.TaskAdapter
import com.example.campustrack.databinding.FragmentTaskBinding
import com.example.campustrack.model.task.Task
import com.example.campustrack.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db by lazy { TaskRoomDatabase(requireContext()) }
        CoroutineScope(Dispatchers.IO).launch {
            val task = db.taskDao().getUnCheckTask()
            val doneTask = db.taskDao().getCheckTask()
            Log.d("TaskFragment", "onViewCreated: ${task}")
            Log.d("TaskFragment", "onDone: ${doneTask}")
            withContext(Dispatchers.Main) {
                val taskAdapter = TaskAdapter(task, db)
                val doneTaskAdapter = TaskAdapter(doneTask, db)
                with(binding) {
                    rvTask.apply {
                        adapter = taskAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                    rvDoneTask.apply {
                        adapter = doneTaskAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                    btnAddTask.setOnClickListener {
                        val action = TaskFragmentDirections.actionTaskFragmentToAddTaskFragment()
                        view.findNavController().navigate(action)
                    }
                }
            }
        }
    }
}