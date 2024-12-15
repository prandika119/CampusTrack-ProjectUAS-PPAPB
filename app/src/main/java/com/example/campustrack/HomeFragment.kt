package com.example.campustrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.campustrack.adapter.SubjectAdapter
import com.example.campustrack.adapter.TaskAdapter
import com.example.campustrack.adapter.TaskHomeAdapter
import com.example.campustrack.databinding.FragmentHomeBinding
import com.example.campustrack.model.schedule.Subject
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.SubjectApi
import com.example.campustrack.network.UserApi
import com.example.campustrack.room.TaskRoomDatabase
import com.example.campustrack.sharePreferences.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: SharedPreferencesManager
    private lateinit var adapter: SubjectAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = SharedPreferencesManager(requireActivity())
        // Show the ProgressBar
        binding.progressBar.visibility = View.VISIBLE

        val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
        retrofit.getAllSubjects().enqueue(object : Callback<List<Subject>> {
            override fun onResponse(call: Call<List<Subject>>, response: Response<List<Subject>>) {
                // Hide the ProgressBar
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val listSubject = response.body()
                    val currentSubject = getCurrentSubject(listSubject)
                    Log.d("HomeFragment", "onResponse: $listSubject")
                    adapter = SubjectAdapter(currentSubject!!)
                    binding.rvJadwalKelas.adapter = adapter
                    binding.rvJadwalKelas.layoutManager = LinearLayoutManager(requireContext())
                }
            }
            override fun onFailure(call: Call<List<Subject>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
                Log.e("HomeFragment", "onFailure: ${t.message}")
            }
        })
        fetchTasksFromDatabase()
        // Retrieve data from SharedPreferences
        val userName = prefManager.getName()
        val userProdi = prefManager.getProdi()

        // Display the retrieved data
        with(binding){
            tvFullName.text = userName
            tvProgramStudi.text = userProdi
            imgLogout.setOnClickListener{
                prefManager.clear()
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }
    }

    fun fetchTasksFromDatabase() {
        val db by lazy { TaskRoomDatabase(requireContext()) }
        CoroutineScope(Dispatchers.IO).launch {
            val task = db.taskDao().getTasksDueTodayOrTomorrow()
            Log.d("HomeFragment", "fetchTasksFromDatabase: $task")
            withContext(Dispatchers.Main) {
                val taskAdapter = TaskHomeAdapter(task, db)
                with(binding) {
                    rvTugas.apply {
                        adapter = taskAdapter
                        layoutManager = LinearLayoutManager(requireContext())
                    }
                }
            }
        }
    }

    fun getCurrentSubject(listSubject: List<Subject>?): List<Subject>? {
        val today = getCurrentDay()
        val currentSubject = listSubject?.filter { it.hari == today }
        Log.d("HomeFragment", "getCurrentSubject: $currentSubject")
        return currentSubject
    }

    fun getCurrentDay(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Minggu"
            Calendar.MONDAY -> "Senin"
            Calendar.TUESDAY -> "Selasa"
            Calendar.WEDNESDAY -> "Rabu"
            Calendar.THURSDAY -> "Kamis"
            Calendar.FRIDAY -> "Jumat"
            Calendar.SATURDAY -> "Sabtu"
            else -> "Unknown"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}