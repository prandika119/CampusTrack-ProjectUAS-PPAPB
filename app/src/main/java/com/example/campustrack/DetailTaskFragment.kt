package com.example.campustrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.campustrack.databinding.FragmentDetailTaskBinding
import com.example.campustrack.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailTaskFragment : Fragment() {
    private var _binding: FragmentDetailTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db by lazy { TaskRoomDatabase(requireContext()) }
        val taskId = arguments?.getInt("taskId") ?: return
        CoroutineScope(Dispatchers.IO).launch {
            val task = db.taskDao().getTask(taskId)
            withContext(Dispatchers.Main) {
                with(binding){
                    tvTitle.text = task.title
                    tvDescription.text = task.description
                    tvDeadline.text = task.deadline
                    if (task.status) {
                        cbStatus.isChecked = true
                    }
                    btnDelete.setOnClickListener{
                        CoroutineScope(Dispatchers.IO).launch {
                            db.taskDao().deleteTask(task)
                            withContext(Dispatchers.Main) {
                                view.findNavController().navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}