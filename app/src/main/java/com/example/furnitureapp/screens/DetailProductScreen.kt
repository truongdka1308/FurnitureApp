package com.example.furnitureapp.screens

import android.util.Log
import com.example.furnitureapp.model.ProductViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.furnitureapp.ProductDetails
import com.example.furnitureapp.model.CartViewModel
import com.example.furnitureapp.model.FavouriteViewModel
import com.example.furnitureapp.model.Product.Product

@Composable
fun DetailProductScreen(productId: String?, navController:NavController, favouriteViewModel: FavouriteViewModel = viewModel(), cartViewModel: CartViewModel = viewModel()) {
    val viewModel: ProductViewModel = viewModel()
    val productId = productId ?: ""
    Log.d("id", "DetailProductScreen: "+productId)
    var product by remember { mutableStateOf<Product?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        if (productId.isNotEmpty()) {
            product = viewModel.fetchProductById(productId)
        }
    }

    if (product != null) {
        // Render UI with product details
        ProductDetails(product = product!!, onBackClick = { navController.popBackStack() }, favouriteViewModel=favouriteViewModel,cartViewModel = cartViewModel)
    } else {
        // Handle case when product is null or not found
        Text(text = "Product not found")
    }
}

