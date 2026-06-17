package com.example.sconn.data_class

data class RegisterRequest(
    val id: Int,
    val fullname: String,
    val email: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val userId: String? = null
)
