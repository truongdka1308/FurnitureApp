package com.example.furnitureapp.response

import com.example.furnitureapp.model.Product.Product

data class ProductResponse(
    val status: Int,
    val message: String,
    val data: List<Product>
)

