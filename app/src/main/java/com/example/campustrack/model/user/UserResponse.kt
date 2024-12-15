package com.example.campustrack.model.user

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("message")
    val message: String,
)
