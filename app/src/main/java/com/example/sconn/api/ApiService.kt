package com.example.sconn.api

import com.example.sconn.data_class.RegisterRequest
import com.example.sconn.data_class.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/register")
    suspend fun submitIdentity(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>
}