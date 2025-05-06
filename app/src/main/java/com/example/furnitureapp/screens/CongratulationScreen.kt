package com.example.furnitureapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.furnitureapp.R
import com.example.furnitureapp.model.CartViewModel

@Composable
fun CongratulationScreen(navController: NavController,cartViewModel: CartViewModel = viewModel()){

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp, top = 124.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(text = "Success!", fontSize = 36.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(44.dp))
        Image(painter = painterResource(id = R.drawable.fur), contentDescription = "furniture")
        Image(painter = painterResource(id = R.drawable.success), contentDescription = "suc")

        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Your order will be delivered soon.", fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = "Thank you for choosing our app!", fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(50.dp))
       Column {
           Button(
               onClick = {
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
               Text(text = "Track Your Orders", modifier = Modifier.padding(vertical = 12.dp))
           }
           Spacer(modifier = Modifier.height(20.dp))
           Button(
               onClick = {
                   cartViewModel.clearCart()
                         navController.navigate("main")
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
               colors = ButtonDefaults.buttonColors(Color.White),
               shape = RoundedCornerShape(8.dp)
           ) {
               Text(text = "BACK TO HOME", modifier = Modifier.padding(vertical = 12.dp), color = Color.Black)
           }
       }
    }
}