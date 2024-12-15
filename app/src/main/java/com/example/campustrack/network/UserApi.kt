package com.example.campustrack.network

import com.example.campustrack.model.user.User
import com.example.campustrack.model.user.UserRequest
import com.example.campustrack.model.user.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @Headers("Content-Type: application/json")
    @POST("table-user")
    fun createUser(@Body userData: UserRequest): Call<UserResponse>

    @GET("table-user")
    fun getAllUsers(): Call<List<User>>

    @GET("table-user/{id}")
    fun getUserById(@retrofit2.http.Path("id") id: String): Call<User>
}