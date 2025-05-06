package com.example.furnitureapp.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.furnitureapp.CustomHeader
import com.example.furnitureapp.R
import com.example.furnitureapp.model.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun CheckoutScreen(navController: NavController,userViewModel: UserViewModel=viewModel(), totalPrice: Double) {
//    val loggedInUser = userViewModel.loggedInUser.observeAsState()
    var deliveryCost by remember { mutableStateOf(0.0) }
    var selectedPaymentMethod by remember { mutableStateOf<String?>(null) }
//    Log.d("TAG", "CheckoutScreen: " + loggedInUser)
    LaunchedEffect(selectedPaymentMethod) {
        if (selectedPaymentMethod == "Fast Delivery") {
            deliveryCost = 5.0
        } else {
            deliveryCost = 0.0
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp)  // Ensure there's space for the checkout button
    ) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomHeader(
            startIconResId = R.drawable.backarrow,
            subTitle = "Check out",
            endIconResId = R.drawable.blank,
            onStartIconClicked = {
                navController.popBackStack()
            }
        )
        TitleSection(title = "Shipping Address")
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .shadow(
                    6.dp,
                    shape = RectangleShape,
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 17.dp)
            ) {
//                loggedInUser.value?.name?.let {
//                    Text(
//                        modifier = Modifier.padding(start = 17.dp),
//                        text = it,
//                        fontSize = 19.sp,
//                        fontWeight = FontWeight.SemiBold
//                    )
//                }
//                Divider(
//                    color = Color.Gray,
//                    thickness = 0.5.dp,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//                loggedInUser.value?.address?.let {
//                    Text(
//                        modifier = Modifier.padding(start = 17.dp),
//                        text = it,
//                        fontSize = 15.sp,
//                        color = Color.Gray
//                    )
//                }
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        TitleSection(title = "Payment")
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .shadow(
                    6.dp,
                    shape = RectangleShape,
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .clickable {
                    selectedPaymentMethod = "Credit Card"
                }
                .background(if (selectedPaymentMethod == "Credit Card") Color.LightGray else Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.creditcard),
                    contentDescription = "credit-card"
                )
                Text(text = "**** **** **** 3947")
            }

        }
        Spacer(modifier = Modifier.height(20.dp))
        TitleSection(title = "Delivery method")
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .shadow(
                    6.dp,
                    shape = RectangleShape,
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .clickable {
                    selectedPaymentMethod = "Fast Delivery"
                    // Update delivery cost based on the selected method
                    deliveryCost = 5.0
                }
                .background( if (selectedPaymentMethod == "Fast Delivery") Color.LightGray else Color.White)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 25.dp)
            ) {
                Image(
                    modifier = Modifier.padding(end = 15.dp),
                    painter = painterResource(id = R.drawable.deliverymethod),
                    contentDescription = "credit-card"
                )
                Text(text = "Fast (2-3days)")
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .shadow(
                    6.dp,
                    shape = RectangleShape,
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.Black
                )
                .background(Color.White)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 15.dp, horizontal = 25.dp)
            ) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Order:", fontSize = 18.sp)
                    Text(text = "$${"%.2f".format(totalPrice)}", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                }
Spacer(modifier = Modifier.height(10.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Delivery:", fontSize = 18.sp)
                    Text(text = "$${"%.2f".format(deliveryCost)}", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = "Total:", fontSize = 18.sp)
                    Text(text = "$${"%.2f".format(totalPrice + deliveryCost)}", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                }

            }

        }


    }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp)
        ) {
            Button(

                    onClick = {
                        if (selectedPaymentMethod == null) {
                            Toast.makeText(
                                navController.context,
                                "Please select a payment method",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            navController.navigate("congratulation")
                        }
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
                Text(text = "Submit Order", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
}
}

@Composable
fun TitleSection(
    title:String?=null
){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        title?.let {
            Text(text = title, fontSize = 18.sp, color = Color.Gray)
        }
        Icon(painter = painterResource(id = R.drawable.edit), contentDescription = "edit")
    }
}


