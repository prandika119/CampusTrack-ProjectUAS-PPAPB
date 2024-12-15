package com.example.campustrack.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.campustrack.R
import com.example.campustrack.databinding.ItemSubjectScheduleBinding
import com.example.campustrack.model.schedule.Subject

class SubjectAdapter (private var listSubject: List<Subject>) : RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>() {
    inner class SubjectViewHolder(private val binding: ItemSubjectScheduleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subject: Subject) {
            with(binding) {
                tvSubjectName.text = subject.matkul
                tvSchedule.text = subject.hari
                tvDosen.text = subject.dosen
                tvTime.text = ", ${subject.waktu}"
                tvCategory.text = "Kuliah"
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding = ItemSubjectScheduleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.bind(listSubject[position])
    }

    override fun getItemCount(): Int {
        Log.d("SubjectAdapter", "getItemCount: ${listSubject.size}")
        return listSubject.size
    }




}