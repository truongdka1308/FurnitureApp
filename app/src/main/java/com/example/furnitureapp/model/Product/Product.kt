package com.example.furnitureapp.model.Product

import com.example.furnitureapp.Category

data class Product(
    val _id: String,
    val name: String,
    val price: Double,
    val img: List<String>?,
    val description: String,
    val rating:String,
    val category: String
)
