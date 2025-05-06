package com.example.furnitureapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.furnitureapp.CustomHeader
import com.example.furnitureapp.R
import com.example.furnitureapp.model.CartViewModel
import com.example.furnitureapp.model.Product.Product
import com.example.furnitureapp.model.UserViewModel

@Composable
fun CartScreen(navController: NavController, userViewModel: UserViewModel= viewModel(), cartViewModel: CartViewModel = viewModel()) {
    var totalPrice by remember { mutableStateOf(0.0) }
//    val loggedInUser = userViewModel.loggedInUser.observeAsState()
//    Log.d("TAG", "CartScreen: "+loggedInUser.value)
    // Calculate total price
    LaunchedEffect(cartViewModel.cartItems) {
        totalPrice = cartViewModel.cartItems.entries.sumOf { it.key.price * it.value }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)  // Ensure there's space for the checkout button
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            CustomHeader(
                subTitle = "My Cart",
                startIconResId = R.drawable.backarrow,
                endIconResId = R.drawable.blank,
                onStartIconClicked = {
                    // Khi người dùng nhấp vào nút back
                    navController.popBackStack()
                }
            )

            // Hiển thị danh sách sản phẩm trong giỏ hàng ở đây
            LazyColumn(
                modifier = Modifier.weight(1f)  // Allow the list to take all available space
            ) {
                items(cartViewModel.cartItems.entries.toList()) { entry ->
                    // Hiển thị thông tin sản phẩm trong giỏ hàng
                    CartItem(
                        navController = navController,
                        productEntry = entry,
                        onUpdateQuantity = { product, quantity ->
                            cartViewModel.updateCartItem(product, quantity) // Gọi hàm cập nhật số lượng trong ViewModel
                            totalPrice = cartViewModel.cartItems.entries.sumOf { it.key.price * it.value }
                        },
                        showCartButton = false,
                        onRemoveItem = { product ->
                            cartViewModel.removeFromCart(product) // Gọi hàm xóa sản phẩm khỏi giỏ hàng trong ViewModel
                            totalPrice = cartViewModel.cartItems.entries.sumOf { it.key.price * it.value }
                        }
                    )
                    // Thêm các thông tin sản phẩm khác tại đây (giá, hình ảnh, ...)
                    Spacer(modifier = Modifier.height(8.dp)) // Khoảng trống giữa các sản phẩm
                }
            }
        }

        if (cartViewModel.cartItems.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = Color.Gray
                    )
                    Text(
                        text = "$${"%.2f".format(totalPrice)}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                Button(
                    onClick = {
                        navController.navigate("checkout/$totalPrice")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            25.dp,
                            shape = RectangleShape,
                            clip = false,
                            ambientColor = Color.Black,
                            spotColor = Color.Black
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Check out", modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
fun CartItem(
    navController: NavController,
    productEntry: Map.Entry<Product, Int>,
    onUpdateQuantity: (Product, Int) -> Unit,
    onRemoveItem: (Product) -> Unit,
    showRemoveButton: Boolean = true,
    showCartButton: Boolean = true,
    showQuantitySection: Boolean = true
) {
    val product = productEntry.key
    var quantity by remember { mutableStateOf(productEntry.value) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { navController.navigate("detail/${product._id}") },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .height(100.dp)
            ) {
                // Load and display image using Coil
                val painter = rememberImagePainter(data = product.img?.get(0))
                Image(
                    painter = painter,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = product.name, fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "$ ${product.price}",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    if (showQuantitySection) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.plus),
                                contentDescription = "icon plus",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        if (quantity < 99) {
                                            quantity++
                                            onUpdateQuantity(product, quantity)
                                        }
                                    }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "$quantity",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Image(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = "icon minus",
                                modifier = Modifier
                                    .size(35.dp)
                                    .clickable {
                                        if (quantity > 1) {
                                            quantity--
                                            onUpdateQuantity(product, quantity)
                                        }
                                    }
                            )
                        }
                    }
                }
                if (showRemoveButton || showCartButton) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (showRemoveButton) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Remove item",
                                modifier = Modifier
                                    .clickable { onRemoveItem(product) }
                            )
                        }
                        if (showCartButton) {
                            Icon(
                                painter = painterResource(id = R.drawable.black_shopping_cart),
                                contentDescription = "Cart icon",
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
