package com.example.furnitureapp.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController,userViewModel: UserViewModel= viewModel()){
    val context = LocalContext.current
    val activity = context as Activity
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                userViewModel.loginWithGoogle(idToken) { result ->
                    result?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    } ?: navController.navigate("main")
                }
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign-in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val quarterHeight = (screenHeight * 0.36f)
    val inputHeight=(screenHeight * 0.04f)
    var passwordVisibility by remember { mutableStateOf(false) }
    Column(
        modifier= Modifier
            .fillMaxSize()


    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
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
            Text(text = "Hello !", fontSize = 30.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "WELCOME BACK",
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
                Text(text = "Forgot Password", modifier = Modifier.align(
                    alignment = Alignment.CenterHorizontally
                ),  fontSize = 18.sp)
                Spacer(modifier = Modifier.height(inputHeight))
                Button(onClick = {
                    userViewModel.loginUser(email, password) { result ->
                        result?.let {
                            Toast.makeText(navController.context, result, Toast.LENGTH_LONG).show()
                        } ?: navController.navigate("main")
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
                    Text(text = "Log in", modifier = Modifier.padding(vertical = 8.dp))
                }


                Spacer(modifier = Modifier.height(screenHeight * 0.03f))
                Text(text = "SIGN UP", fontSize = 18.sp,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(end = 10.dp)
                        .clickable {
                            navController.navigate("register")
                        }
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.03f))
//
                // "OR" separator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray))
                    Text(
                        text = "OR",
                        modifier = Modifier.padding(horizontal = 12.dp),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                    Box(modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(Color.Gray))
                }

                Spacer(modifier = Modifier.height(10.dp))

// Google login button
                Button(
                    onClick = {
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken("1036364306472-p78okjtshkrbgi7hbvs4drseb7hfd2g3.apps.googleusercontent.com") // <--- Thay bằng client ID từ Firebase
                            .requestEmail()
                            .build()

                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        googleSignInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp)
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google), // Đặt icon Google của bạn ở drawable
                        contentDescription = "Google Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                    Text(text = "Continue with Google", color = Color.Black)
                }

//
            }

        }

    }
}