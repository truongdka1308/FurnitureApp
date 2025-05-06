package com.example.furnitureapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.furnitureapp.api.HttpRequest
import com.example.furnitureapp.model.Product.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product
    private val apiService = HttpRequest.getInstance()
    suspend fun fetchProductsByCategory(categoryId: String) {
        try {
            Log.d("kkk", "fetchProductsByCategory: "+categoryId)
            val response = apiService.getProductsByCategory(categoryId)
            if (response.status == 200) {
                _product.value = response.data
            } else {
                Log.e("ProductViewModel", "Lỗi: ${response.message}")
            }
        } catch (e: Exception) {
            Log.e("ProductViewModel", "Exception: ${e.message}")
        }
    }
    suspend fun fetchProductById(productId: String): Product? {
        return try {
            val response = apiService.getProductById(productId)
            if (response.status == 200) {
                response.data
            } else {
                Log.e("ProductViewModel", "Lỗi: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("ProductViewModel", "Exception: ${e.message}")
            null
        }
    }
    suspend fun fetchAllProduct(){
        try {
            val response=apiService.getProducts();
            if (response.status==200){
                _products.value = response.data
            }else{
                Log.e("fetchallproduct", "Lỗi: ${response.message}")
                null
            }
        }catch (e:Exception){
            Log.e("fetchallproduct", "Exception: ${e.message}")
            null
        }
    }
}
