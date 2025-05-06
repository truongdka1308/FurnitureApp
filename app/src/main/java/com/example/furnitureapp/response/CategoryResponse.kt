package com.example.furnitureapp.response

import com.example.furnitureapp.Category

data class CategoryResponse(
    val status: Int,
    val message: String,
    val data: List<Category>
)

