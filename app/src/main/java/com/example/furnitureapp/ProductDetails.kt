package com.example.furnitureapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.furnitureapp.model.CartViewModel
import com.example.furnitureapp.model.FavouriteViewModel
import com.example.furnitureapp.model.Product.Product
import kotlin.math.roundToInt

@Composable
fun ProductDetails(product: Product, onBackClick: () -> Unit, favouriteViewModel: FavouriteViewModel, cartViewModel: CartViewModel) {
    val checkStatus by favouriteViewModel.checkFavouriteStatus.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var triggerCheck by remember { mutableStateOf(false) }
    LaunchedEffect(product._id) {
        favouriteViewModel.checkFavourite(product._id)
    }
    val isFavourite = checkStatus?.status == 200
    var selectedImage by remember {
        mutableStateOf(product.img?.first())
    }
    var quantity by remember { mutableStateOf(1) }
    Column(
        modifier = Modifier
            .fillMaxSize(),

    ) {
        Box(
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .padding(start = 52.dp)
        ) {
            // Hiển thị hình ảnh sản phẩm
            Image(
                painter = rememberImagePainter(data = selectedImage),
                contentDescription = product.description,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = 74.dp)),
                contentScale = ContentScale.Crop,
            )

            // Biểu tượng quay lại đè lên hình ảnh
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "Back",
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (-50).dp
                                .toPx()
                                .roundToInt(), // Dịch chuyển icon ra ngoài một nửa
                            y = 0.dp
                                .toPx()
                                .roundToInt()
                        )
                    }
                    .align(Alignment.TopStart)
                    .size(110.dp)
                    .clickable(
                        onClick = onBackClick,
                        indication = rememberRipple(radius = 40.dp, bounded = false),
                        interactionSource = remember { MutableInteractionSource() }
                    )

            )

            Column (

                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 50.dp)
                    .offset {
                        IntOffset(
                            x = (-30).dp
                                .toPx()
                                .roundToInt(), // Dịch chuyển icon ra ngoài một nửa
                            y = -46.dp
                                .toPx()
                                .roundToInt()
                        )
                    }
                    .background(Color(color = 0xFFFFFCFC), shape = RoundedCornerShape(40.dp))
                    .height(192.dp)
                    .width(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly



            ){
Image(painter = painterResource(id = R.drawable.w_circle), contentDescription = "w", modifier = Modifier.size(34.dp).clickable { selectedImage=product.img?.first() })
Image(painter = painterResource(id = R.drawable.b_circle), contentDescription = "w", modifier = Modifier.size(40.dp).clickable { selectedImage=
    product.img?.get(1)
})
Image(painter = painterResource(id = R.drawable.y_circle), contentDescription = "w", modifier = Modifier.size(40.dp).clickable { selectedImage=
    product.img?.get(2)
})
            }

        }

        Spacer(modifier = Modifier.height(25.dp))

        // Hiển thị tên sản phẩm
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = gelasioFontFamily,
            modifier = Modifier.padding(start = 30.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Hiển thị giá sản phẩm
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "$ ${product.price}",
                style = TextStyle(fontSize = 30.sp),
                fontWeight = FontWeight.Bold,
modifier = Modifier.padding(end = 20.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,


            ){
                Image(painter = painterResource(id =R.drawable.plus),
                    contentDescription = "icon plus",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { if (quantity < 99) quantity++ }
                    )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "$quantity")
                Spacer(modifier = Modifier.width(12.dp))
                Image(modifier = Modifier
                    .size(30.dp)
                    .clickable { if (quantity > 1) quantity-- },painter = painterResource(id =R.drawable.minus),
                    contentDescription = "icon minus")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
Row (
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 25.dp, bottom = 10.dp),
    verticalAlignment = Alignment.CenterVertically

){
    Icon(imageVector = Icons.Default.Star, contentDescription = "star",
tint = Color.Yellow
        )
    Text(text = product.rating,
        style = TextStyle(fontSize = 20.sp),
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 10.dp, end = 20.dp)
        )
    Text(text = "(50 reviews)")
}


        // Hiển thị mô tả sản phẩm
        Text(
            text = product.description,
            style = TextStyle(fontSize = 16.sp),
            modifier = Modifier.padding(horizontal = 30.dp),
            color = Color.Gray
        )
        Row (
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){

            LaunchedEffect(triggerCheck) {
                if (triggerCheck) {
                    kotlinx.coroutines.delay(500) // Đợi backend xử lý xong
                    favouriteViewModel.checkFavourite(product._id)
                    isLoading = false
                    triggerCheck = false
                }
            }

            IconButton(
                onClick = {
                    if (isLoading) return@IconButton
                    isLoading = true

                    if (isFavourite) {
                        favouriteViewModel.deleteFavourite(product._id)
                    } else {
                        favouriteViewModel.addToFavourite(product._id)
                    }

                    triggerCheck = true
                },
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE9E7E7), shape = RoundedCornerShape(5.dp)),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Image(
                        painter = if (isFavourite)
                            painterResource(id = R.drawable.fillbookmark)
                        else
                            painterResource(id = R.drawable.outbookmark),
                        contentDescription = "Add to favourites",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.width(15.dp))
            Button(
                onClick = { cartViewModel.addToCart(product, quantity) },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(
                        25.dp,
                        shape = RectangleShape,
                        clip = false,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ),
                colors = androidx.compose.material.ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Add to cart", modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                    textAlign = TextAlign.Center, color = Color.White,
                    fontSize = 20.sp
                    )
            }
        }
    }
}


