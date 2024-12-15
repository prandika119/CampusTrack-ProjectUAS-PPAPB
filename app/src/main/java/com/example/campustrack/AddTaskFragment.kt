package com.example.campustrack

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.campustrack.databinding.FragmentAddTaskBinding
import com.example.campustrack.databinding.FragmentTaskBinding
import com.example.campustrack.model.task.Task
import com.example.campustrack.room.TaskRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db by lazy { TaskRoomDatabase(requireContext()) }
        with(binding){
            etDeadline.setOnClickListener {
                showDatePickerDialog()
            }
            btnAddTask.setOnClickListener{
                CoroutineScope(Dispatchers.IO).launch {
                    val task = Task(
                        id = 0,
                        title = etTitle.text.toString(),
                        description = etDescription.text.toString(),
                        deadline = etDeadline.text.toString()
                    )
                    Log.d("AddTaskFragment", "task: $task")
                    db.taskDao().addTask(task)
                    Log.d("AddTaskFragment", "Task successfully added to database")
                    withContext(Dispatchers.Main) {
                        findNavController().navigateUp()
                    }
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Menggunakan format yyyy-MM-dd untuk konsistensi
                val selectedDate = String.format(
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                binding.etDeadline.setText(selectedDate)
            },
            year, month, day
        )
        // Opsional: Set tanggal minimal ke hari ini
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}