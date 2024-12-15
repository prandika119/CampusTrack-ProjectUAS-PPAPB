package com.example.campustrack.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("prodi")
    val prodi: String,
    @SerializedName("angkatan")
    val angkatan: Int,
)
