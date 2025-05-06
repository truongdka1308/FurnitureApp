package com.example.furnitureapp

sealed class Screen {
    object Home : Screen()
    object Favorite : Screen()
    object Notification : Screen()
    object Profile : Screen()
}