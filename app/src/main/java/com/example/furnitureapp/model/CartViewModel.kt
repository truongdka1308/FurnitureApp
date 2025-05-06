package com.example.furnitureapp.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateMapOf
import com.example.furnitureapp.model.Product.Product

class CartViewModel : ViewModel() {
    // Danh sách sản phẩm trong giỏ hàng
    val cartItems = mutableStateMapOf<Product, Int>()
    // Thêm sản phẩm vào giỏ hàng
    fun addToCart(product: Product, quantity: Int) {
        if (cartItems.containsKey(product)) {
            // Nếu sản phẩm đã có trong giỏ hàng, cộng thêm số lượng
            cartItems[product] = cartItems[product]!! + quantity
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, thêm mới
            cartItems[product] = quantity
        }
    }
    fun updateCartItem(product: Product, quantity: Int) {
        cartItems[product] = quantity
    }
    // Xóa sản phẩm khỏi giỏ hàng
    fun removeFromCart(product: Product) {
        cartItems -= product
    }
    fun clearCart() {
        cartItems.clear()
    }

}
