package com.example.furnitureapp.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furnitureapp.Category
import com.example.furnitureapp.api.HttpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val apiService = HttpRequest.getInstance()

    // Fetch categories from API
    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = apiService.getCategories()
                _categories.value = response.data
            } catch (e: Exception) {
                Log.e("API_Error", "Error fetching categories: ${e.message}")
            }
        }

    }
}
