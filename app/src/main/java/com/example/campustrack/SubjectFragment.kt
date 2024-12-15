package com.example.campustrack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.campustrack.adapter.AllSubjectAdapter
import com.example.campustrack.databinding.FragmentSubjectBinding
import com.example.campustrack.model.schedule.Subject
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.SubjectApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubjectFragment : Fragment() {
    private var _binding: FragmentSubjectBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            progressBar.visibility = View.VISIBLE
            addSchedule.setOnClickListener{
                val action = SubjectFragmentDirections.actionSubjectFragmentToAddScheduleFragment()
                view.findNavController().navigate(action)
            }
        }

        val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
        retrofit.getAllSubjects().enqueue(object : Callback<List<Subject>> {
            override fun onResponse(call: Call<List<Subject>>, response: Response<List<Subject>>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val listSubject = response.body()
                    val adapter = AllSubjectAdapter(listSubject!!)
                    binding.rvSubjectSchedule.adapter = adapter
                    binding.rvSubjectSchedule.layoutManager = LinearLayoutManager(requireContext())
                }
            }
            override fun onFailure(call: Call<List<Subject>>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
                Log.e("SubjectFragment", "onFailure: ${t.message}")
            }
        })
    }
}