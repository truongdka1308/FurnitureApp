package com.example.furnitureapp.manager

object TokenManager {
    var token: String? = null
    var userId: String? = null
    fun clear() {
        token = null
        userId = null
    }
}
