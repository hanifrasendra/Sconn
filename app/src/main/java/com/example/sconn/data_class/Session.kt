package com.example.sconn.data_class

data class Session(
    val id: Int,
    val userId: Int,
    val title: String,
    val subject: String,
    val date: String,
    val time: String,
    val placeName: String,
    val fullAddress: String,
    val location: String,
    val photoPath: String,
    val description: String
)
