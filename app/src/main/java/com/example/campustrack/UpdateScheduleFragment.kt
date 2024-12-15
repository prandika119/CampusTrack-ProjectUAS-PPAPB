package com.example.campustrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.campustrack.databinding.FragmentUpdateScheduleBinding
import com.example.campustrack.model.schedule.Subject
import com.example.campustrack.model.schedule.SubjectRequest
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.SubjectApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateScheduleFragment : Fragment() {

    private var _binding: FragmentUpdateScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subjectId = arguments?.getString("subjectId") ?: return
        binding.progressBar.visibility = View.VISIBLE
        fetchData(subjectId)
        with(binding){
            btnUpdateSchedule.setOnClickListener {
                updateSubject(subjectId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun fetchData(subjectId: String){
        val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
        retrofit.getSubject(subjectId).enqueue(object : Callback<Subject> {
            override fun onResponse(p0: Call<Subject>, response: Response<Subject>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val subject = response.body()
                    with(binding){
                        etSubject.setText(subject?.matkul)
                        etDay.setText(subject?.hari)
                        etTime.setText(subject?.waktu)
                        etLecturer.setText(subject?.dosen)
                    }

                }
            }

            override fun onFailure(p0: Call<Subject>, p1: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun updateSubject(subjectId: String){
        val subject = binding.etSubject.text.toString()
        val day = binding.etDay.text.toString()
        val time = binding.etTime.text.toString()
        val lecturer = binding.etLecturer.text.toString()
        if (subject.isEmpty() || day.isEmpty() || time.isEmpty() || lecturer.isEmpty()) {
            Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }
    binding.progressBar.visibility = View.VISIBLE
        val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
        retrofit.updateSubject(subjectId, SubjectRequest(day, subject, lecturer, time)).enqueue(object : Callback<Void> {
            override fun onResponse(p0: Call<Void>, response: Response<Void>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Berhasil Update Data", Toast.LENGTH_SHORT).show()
                    requireView().findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Gagal Update Data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<Void>, p1: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
            }

        })
    }

}