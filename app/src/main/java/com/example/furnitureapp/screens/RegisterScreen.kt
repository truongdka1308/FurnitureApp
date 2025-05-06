package com.example.furnitureapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.furnitureapp.R
import com.example.furnitureapp.gelasioFontFamily
import com.example.furnitureapp.model.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController,userViewModel: UserViewModel= viewModel()){
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val quarterHeight = (screenHeight * 0.36f)
    val inputHeight=(screenHeight * 0.04f)
    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        modifier= Modifier
            .fillMaxSize()


    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var cfpassword by remember { mutableStateOf("") }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 10.dp, start = 30.dp, end = 30.dp)

        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Color.Gray)
            )
            Image(
                painter = painterResource(id = R.drawable.furniture), // Replace with your image resource
                contentDescription = "Header Image",
                modifier = Modifier
                    .size(64.dp)
                    .padding(horizontal = 8.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(Color.Gray)
            )
        }

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 30.dp)
        ) {

            Text(
                text = "WELCOME",
                fontSize = 28.sp,
                fontFamily = gelasioFontFamily,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(inputHeight))
        // Box with shadow and background color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
                .shadow(
                    5.dp,
                    shape = RectangleShape,
                    clip = false,
                    ambientColor = Color.Black,
                    spotColor = Color.Gray
                )
                .background(Color(0xFFF5F5F5))
                .padding(start = 30.dp)


        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Name", color = Color.Gray)
                TextField(
                    value = name, onValueChange = { name = it },
                    colors = TextFieldDefaults.textFieldColors(

                        containerColor = Color(0xFFF5F5F5),
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(inputHeight))

                Text(text = "Email", color = Color.Gray)
                TextField(
                    value = email, onValueChange = { email = it },
                    colors = TextFieldDefaults.textFieldColors(

                        containerColor = Color(0xFFF5F5F5),
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(inputHeight))

                Text(text = "Password", color = Color.Gray)
                TextField(
                    value = password, onValueChange = { password = it },
                    colors = TextFieldDefaults.textFieldColors(

                        containerColor = Color(0xFFF5F5F5),
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray
                    ),

                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Image(
                            painterResource(id = if (passwordVisibility) R.drawable.eye else R.drawable.eye)  ,
                            contentDescription = "Toggle password visibility",
                            modifier = Modifier
                                .clickable { passwordVisibility = !passwordVisibility }
                                .size(22.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(inputHeight))

                Text(text = "Confirm Password", color = Color.Gray)
                TextField(
                    value = cfpassword, onValueChange = { cfpassword = it },
                    colors = TextFieldDefaults.textFieldColors(

                        containerColor = Color(0xFFF5F5F5),
                        cursorColor = Color.Black,
                        focusedIndicatorColor = Color.Gray,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color.Blue,
                        unfocusedLabelColor = Color.Gray
                    ),

                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        Image(
                            painterResource(id = if (passwordVisibility) R.drawable.eye else R.drawable.eye)  ,
                            contentDescription = "Toggle password visibility",
                            modifier = Modifier
                                .clickable { passwordVisibility = !passwordVisibility }
                                .size(22.dp)
                        )
                    }
                )


                Spacer(modifier = Modifier.height(inputHeight))
                Button(onClick = {
                    userViewModel.registerUser(name, email, password) { result ->
                        result?.let {
                            Toast.makeText(navController.context, result, Toast.LENGTH_LONG).show()
                        } ?: navController.navigate("login")
                    }

                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
                    .shadow(
                        25.dp,
                        shape = RectangleShape,
                        clip = false,
                        ambientColor = Color.Black,
                        spotColor = Color.Black
                    ),
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    shape = RoundedCornerShape(8.dp),

                    ) {
                    Text(text = "SIGN UP", modifier = Modifier.padding(vertical = 8.dp))
                }


                Spacer(modifier = Modifier.height(screenHeight * 0.03f))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(text = "Already have account?", fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier

                            .padding(end = 5.dp)
                    )
                    Text(text = "SIGN IN", fontSize = 18.sp,
                        style = TextStyle(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight * 0.03f))
            }

        }

    }
}
