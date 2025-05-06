package com.example.furnitureapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.furnitureapp.model.FavouriteViewModel
import com.example.furnitureapp.model.Product.Product

@Composable
fun FavouriteScreen(navController: NavController, favouriteViewModel: FavouriteViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        favouriteViewModel.fetchFavourites()
    }
    Column {
        // Header
        CustomHeader(
            subTitle = "Yêu thích",
            startIconResId = R.drawable.search,
            onStartIconClicked = {
                // Khi người dùng nhấp vào nút back
                navController.navigate("search")
            },
                    endIconResId = R.drawable.cart_icon,
            onEndIconClicked = {
                // Khi người dùng nhấp vào nút giỏ hàng
                navController.navigate("cart")
            }
        )

        // Hiển thị danh sách sản phẩm yêu thích ở đây
        if (favouriteViewModel.favourites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Không có sản phẩm yêu thích nào.", color = Color.Gray, fontSize = 18.sp)
            }
        } else {
            LazyColumn {
                items(favouriteViewModel.favourites) { product ->
                    FavouriteCartItem(
                        navController = navController,
                        productEntry = mapOf(product to 1).entries.first(),
                        onRemoveItem = { favouriteViewModel.deleteFavourite(it._id) }
                    )
                }
            }
        }

    }

}


@Composable
fun FavouriteCartItem(
    navController: NavController,
    productEntry: Map.Entry<Product, Int>,
    onRemoveItem: (Product) -> Unit,
    showCartButton: Boolean = true,
    showRemoveButton:Boolean =true,
) {
    val product = productEntry.key

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("detail/${product._id}") },
        shape = RoundedCornerShape(8.dp),

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
                    Text(text = "$ ${product.price}",fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
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

