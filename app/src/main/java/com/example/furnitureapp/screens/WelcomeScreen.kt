package com.example.furnitureapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.furnitureapp.R
import com.example.furnitureapp.gelasioFontFamily

@Composable
fun WelcomeScreen(navController: NavController){
    // Code của WelcomeScreen ở đây...
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(id = R.drawable.welcomeimg),
            contentScale = ContentScale.Crop,
            contentDescription = "Welcome Image",modifier=Modifier.fillMaxSize(),

            )
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val quarterHeight = (screenHeight * 0.36f)
        val topHeight=(screenHeight * 0.65f)

        Column(

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = quarterHeight)
        ){

            Text(
                text = "MAKE YOUR",
                color = Color(0xFF606060),
                fontSize = 30.sp,
                fontFamily = gelasioFontFamily,
                style = TextStyle(lineHeight = 30.47.sp),
                modifier = Modifier.padding(start = 30.dp)
            )
            Text(
                text = "HOME BEAUTIFUL",
                color = Color.Black,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = gelasioFontFamily,
                style = TextStyle(lineHeight = 38.09.sp),
                modifier = Modifier.padding(start = 30.dp)
            )

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier=Modifier.padding(20.dp)
        ) {
            Text(
                text = "The best simple place where you discover most wonderful furnitures and make your home beautiful",
                color = Color(0xFF808080) ,
                fontSize = 19.sp,
                style = TextStyle(lineHeight = 36.sp),


                modifier = Modifier.padding(start = 40.dp)
            )
        }

        Button(onClick = { navController.navigate("Login") },
            shape = RectangleShape,
            modifier = Modifier
                .padding(top = topHeight)
                .align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF242424)
            ),
        ) {
            Text(text = "Get Started", fontSize = 20.sp,
                fontFamily = gelasioFontFamily,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(bottom = 5.dp),
                style = TextStyle(lineHeight = 22.85.sp),

                )
        }

    }
}
