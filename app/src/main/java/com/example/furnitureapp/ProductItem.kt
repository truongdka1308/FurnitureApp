package com.example.furnitureapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.furnitureapp.model.Product.Product


@Composable
fun ProductItem(navController:NavController,product: Product) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("detail/${product._id}") },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Load and display image using Coil
            Box {
                val imageUrl = product.img?.getOrNull(0) ?: "" // fallback nếu null
                val painter = rememberImagePainter(data = imageUrl)

                Image(
                    painter = painter,
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(190.dp)
                        .width(190.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                )

                Icon(
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(13.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {

                Text(text = product.name, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$ ${product.price}", color = Color.Black, fontWeight = FontWeight.Bold)

            }
        }
    }
}



