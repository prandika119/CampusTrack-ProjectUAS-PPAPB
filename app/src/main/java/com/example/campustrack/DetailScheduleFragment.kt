package com.example.campustrack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.campustrack.databinding.FragmentDetailScheduleBinding
import com.example.campustrack.databinding.FragmentSubjectBinding
import com.example.campustrack.model.schedule.Subject
import com.example.campustrack.network.ApiClient
import com.example.campustrack.network.SubjectApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailScheduleFragment : Fragment() {
    private var _binding: FragmentDetailScheduleBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val subjectId = arguments?.getString("subjectId") ?: return
        with(binding){
            progressBar.visibility = View.VISIBLE
            editSchedule.setOnClickListener{
                val action = DetailScheduleFragmentDirections.actionDetailScheduleFragmentToUpdateScheduleFragment(subjectId)
                view.findNavController().navigate(action)
            }
            btnDelete.setOnClickListener{
                binding.progressBar.visibility = View.VISIBLE
                deleteSubject(subjectId)
            }
        }
        fetchData(subjectId)
    }

    fun fetchData(subjectId: String){
        val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
        retrofit.getSubject(subjectId).enqueue(object : Callback<Subject> {
            override fun onResponse(p0: Call<Subject>, response: Response<Subject>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val subject = response.body()
                    with(binding){
                        tvSubjectName.text = subject?.matkul
                        tvSchedule.text = "${subject?.hari}, "
                        tvTime.text = subject?.waktu
                        tvDosen.text = subject?.dosen
                        card.visibility = View.VISIBLE
                    }

                }
            }

            override fun onFailure(p0: Call<Subject>, p1: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun deleteSubject(subjectId: String){
        val retrofit = ApiClient.getInsance().create(SubjectApi::class.java)
        retrofit.deleteSubject(subjectId).enqueue(object : Callback<Void> {
            override fun onResponse(p0: Call<Void>, response: Response<Void>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    view?.findNavController()?.navigateUp()
                }
            }
            override fun onFailure(p0: Call<Void>, p1: Throwable) {
                Toast.makeText(requireContext(), "Periksa Koneksi Anda", Toast.LENGTH_SHORT).show()
            }
        })
    }

}