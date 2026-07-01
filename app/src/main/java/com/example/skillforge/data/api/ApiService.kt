package com.example.skillforge.data.api

import com.example.skillforge.data.model.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("android-assesment/notes/refs/heads/main/data.json")
    suspend fun getCourses(): ApiResponse

}