package com.example.campustrack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.campustrack.SubjectFragmentDirections
import com.example.campustrack.databinding.ItemAllSubjectScheduleBinding
import com.example.campustrack.model.schedule.Subject

class AllSubjectAdapter (private var listSubject: List<Subject>): RecyclerView.Adapter<AllSubjectAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllSubjectAdapter.SubjectViewHolder {
        val binding = ItemAllSubjectScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(listSubject[position])
    }

    override fun getItemCount(): Int {
        return listSubject.size
    }

    inner class SubjectViewHolder(private val binding: ItemAllSubjectScheduleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subject: Subject) {
            with(binding) {
                tvSubjectName.text = subject.matkul
                tvDay.text = subject.hari
                tvTime.text = ", ${subject.waktu}"
                card.setOnClickListener{
                    val action = SubjectFragmentDirections.actionSubjectFragmentToDetailScheduleFragment(subject.id)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

}