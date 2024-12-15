package com.example.campustrack.model.user

import com.google.gson.annotations.SerializedName

data class UserRequest (
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("prodi")
    val prodi: String? = null,
    @SerializedName("angkatan")
    val angkatan: Int? = null,
)