package com.example.campustrack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.campustrack.databinding.FragmentAddScheduleBinding
import com.example.campustrack.model.schedule.SubjectRequest
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.SubjectApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddScheduleFragment : Fragment() {
    private var _binding: FragmentAddScheduleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnAddSchedule.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val subject = etSubject.text.toString()
                val day = etDay.text.toString()
                val time = etTime.text.toString()
                val lecturer = etLecturer.text.toString()
                if (subject.isEmpty() || day.isEmpty() || time.isEmpty() || lecturer.isEmpty()) {
                    Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }
                Log.d("AddScheduleFragment", "onViewCreated: $subject $day $time $lecturer")
                var subjectRequest = SubjectRequest(hari = day, matkul = subject, dosen = lecturer,  waktu = time)
                Log.d("AddScheduleFragment", "onViewCreated: $subjectRequest")
                val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
                retrofit.addSubject(subjectRequest).enqueue(object : Callback<Void> {
                    override fun onResponse(p0: Call<Void>, response: Response<Void>) {
                        progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            findNavController().navigateUp()
                        }
                    }

                    override fun onFailure(p0: Call<Void>, p1: Throwable) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Periksan Koneksi Anda", Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }
    }
}