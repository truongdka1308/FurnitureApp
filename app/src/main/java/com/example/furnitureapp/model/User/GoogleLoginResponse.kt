package com.example.furnitureapp.model.User

import com.example.furnitureapp.api.UserRP

data class GoogleLoginResponse(
    val status: Int,
    val message: String,
    val data: UserRP,
    val token: String
)
