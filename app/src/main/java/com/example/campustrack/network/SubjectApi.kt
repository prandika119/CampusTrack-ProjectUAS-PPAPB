package com.example.campustrack.network

import androidx.room.Delete
import com.example.campustrack.model.schedule.Subject
import com.example.campustrack.model.schedule.SubjectRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface SubjectApi {
    @GET("table-schedule")
    fun getAllSubjects(): Call<List<Subject>>


    @Headers("Content-Type: application/json")
    @POST("table-schedule")
    fun addSubject(@Body dataSubject: SubjectRequest): Call<Void>

    @GET("table-schedule/{id}")
    fun getSubject(@Path("id") id: String): Call<Subject>

    @POST("table-schedule/{id}")
    fun updateSubject(@Path("id") id: String, @Body dataSubject: SubjectRequest): Call<Void>

    @DELETE("table-schedule/{id}")
    fun deleteSubject(@Path("id") id: String): Call<Void>
}