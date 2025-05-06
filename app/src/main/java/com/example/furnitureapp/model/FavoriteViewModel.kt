package com.example.furnitureapp.model

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furnitureapp.api.ApiService // Import đúng ApiService bạn đã setup Retrofit
import com.example.furnitureapp.api.HttpRequest
import com.example.furnitureapp.manager.TokenManager
import com.example.furnitureapp.model.Favourite.Favourite
import com.example.furnitureapp.model.Product.Product
import com.example.furnitureapp.response.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel : ViewModel() {

    private val _addFavouriteStatus = MutableStateFlow<ApiResponse<Favourite>?>(null)
    val addFavouriteStatus: StateFlow<ApiResponse<Favourite>?> = _addFavouriteStatus

    //check
    private val _checkFavouriteStatus = MutableStateFlow<ApiResponse<Favourite>?>(null)
    val checkFavouriteStatus: StateFlow<ApiResponse<Favourite>?> = _checkFavouriteStatus

    //del
    private val _deleteFavouriteStatus = MutableStateFlow<ApiResponse<String>?>(null)
    val deleteFavouriteStatus: StateFlow<ApiResponse<String>?> = _deleteFavouriteStatus

    private val _favourites = mutableStateListOf<Product>()
    val favourites: List<Product> get() = _favourites

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val apiService = HttpRequest.getInstance()

    fun addToFavourite(productId: String) {
        viewModelScope.launch {
            _isLoading.value = true // Bắt đầu loading
            try {
                val userId = TokenManager.userId
                if (userId != null) {
                    val favourite = Favourite(userId, productId)
                    val response = apiService.addToFavourite(userId, favourite)
                    _addFavouriteStatus.value = response
                } else {
                    _addFavouriteStatus.value = ApiResponse(
                        status = 401,
                        message = "User not logged in",
                        data = Favourite("", productId)
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _addFavouriteStatus.value = ApiResponse(
                    status = 500,
                    message = "Error: ${e.message}",
                    data = Favourite("", productId)
                )
            } finally {
                _isLoading.value = false // Kết thúc loading
            }
        }
    }
    fun checkFavourite(productId: String) {
        viewModelScope.launch {
            try {
                val userId = TokenManager.userId
                if (userId != null) {
                    val response = apiService.checkFavourite(userId, productId)
                    _checkFavouriteStatus.value = response
                } else {
                    _checkFavouriteStatus.value = ApiResponse(
                        status = 401,
                        message = "User not logged in",
                        data = Favourite("", productId)
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _checkFavouriteStatus.value = ApiResponse(
                    status = 500,
                    message = "Error: ${e.message}",
                    data = Favourite("", productId)
                )
            }
        }
    }

    fun deleteFavourite(productId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val userId = TokenManager.userId
                if (userId != null) {
                    val response = apiService.deleteFavourite(userId, productId)
                    if (response.status == 200) {
                        fetchFavourites()
                    } else {
                        Log.e("FavouriteViewModel", "Xóa thất bại: ${response.message}")
                    }
                }
            } catch (e: Exception) {
                Log.e("FavouriteViewModel", "Lỗi khi xóa sản phẩm yêu thích: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchFavourites() {
        viewModelScope.launch {
            try {
                val userId = TokenManager.userId
                if (userId != null) {
                    val response = apiService.getFavourites(userId)
                    if (response.status == 200) {
                        _favourites.clear()
                        _favourites.addAll(response.data)
                    }
                }
            } catch (e: Exception) {
                Log.e("FavouriteViewModel", "Lỗi khi fetch: ${e.message}")
            }
        }
    }
}

