package com.example.furnitureapp.response

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T
)

