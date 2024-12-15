package com.example.campustrack.model.schedule

import com.google.gson.annotations.SerializedName

data class Subject(
    @SerializedName("_id")
    val id: String,

    @SerializedName("hari")
    val hari: String,

    @SerializedName("matkul")
    val matkul: String,

    @SerializedName("dosen")
    val dosen: String,

    @SerializedName("waktu")
    val waktu: String,
)
