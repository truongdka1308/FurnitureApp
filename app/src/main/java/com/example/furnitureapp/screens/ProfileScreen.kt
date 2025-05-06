package com.example.furnitureapp.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.furnitureapp.CustomHeader
import com.example.furnitureapp.R
import com.example.furnitureapp.api.User
import com.example.furnitureapp.manager.TokenManager
import com.example.furnitureapp.model.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun ProfileScreen(navController: NavController, googleSignInClient: GoogleSignInClient, userViewModel: UserViewModel = viewModel()) {
    val loggedInUser by userViewModel.loggedInUser.observeAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        val userId = TokenManager.userId
        if (userId != null) {
            userViewModel.fetchUserInfo(userId)
        }
    }
    Column {
        CustomHeader(
            startIconResId = R.drawable.search,
            subTitle = "Profile",
            endIconResId = R.drawable.logout,
            onEndIconClicked = {
                showLogoutDialog = true
            }
        )

        if (showLogoutDialog) {
            LogoutDialog(
                onConfirm = {
                    showLogoutDialog = false
                    googleSignInClient.signOut().addOnCompleteListener {
                        TokenManager.clear() // nếu bạn lưu token
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                },
                onDismiss = { showLogoutDialog = false }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, start = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val avatarUrl = loggedInUser?.avatarUrl

            if (avatarUrl.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Default Avatar",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = "Avatar",
                    placeholder = painterResource(R.drawable.y_circle),
                    error = painterResource(R.drawable.b_circle),
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
            }

            Column(modifier = Modifier.padding(start = 10.dp)) {
                Text(
                    text = loggedInUser?.name ?: "Guest",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = loggedInUser?.email ?: "",
                    color = Color.Gray
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            CustomSettingItem(
                title = "My orders",
                detail = "Already have 10 orders",
                endIconResId = Icons.Default.KeyboardArrowRight
            )
            Spacer(modifier = Modifier.height(20.dp))

            CustomSettingItem(
                title = "Shipping Addresses",
                detail = "03 Addresses",
                endIconResId = Icons.Default.KeyboardArrowRight
            )
            Spacer(modifier = Modifier.height(20.dp))

            CustomSettingItem(
                title = "Payment Method",
                detail = "You have 2 cards",
                endIconResId = Icons.Default.KeyboardArrowRight
            )
            Spacer(modifier = Modifier.height(20.dp))

            CustomSettingItem(
                title = "My reviews",
                detail = "Reviews for 5 items",
                endIconResId = Icons.Default.KeyboardArrowRight
            )
            Spacer(modifier = Modifier.height(20.dp))

            CustomSettingItem(
                title = "Setting",
                detail = "Notification, Password, FAQ, Contact",
                endIconResId = Icons.Default.KeyboardArrowRight
            )
        }
    }
}


@Composable
fun CustomSettingItem(
    title:String? = null,
    detail:String? =null,
    endIconResId: ImageVector? = null,
    onEndIconClicked: () -> Unit = {}

){
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(
                6.dp,
                shape = RectangleShape,
                clip = false,
                ambientColor = Color.Black,
                spotColor = Color.Gray
            )
            .background(Color.White)
    ){

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.padding(17.dp)
            ){
                title?.let {
                    Text(text = title, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                detail?.let {
                    Text(text = detail, color = Color.Gray)
                }
            }
            endIconResId?.let { iconResid->
                IconButton(onClick = { onEndIconClicked() }) {
                    Icon(modifier = Modifier.padding(end = 10.dp),imageVector = iconResid, contentDescription = "arrow")
                }
            }
        }
    }
}

@Composable
fun LogoutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Logout", fontWeight = FontWeight.Bold) },
        text = { Text(text = "Are you sure you want to log out?") },
        confirmButton = {
            Button( colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(8.dp),onClick = { onConfirm() }) {
                Text("Yes", color = Color.White)
            }
        },
        dismissButton = {
            Button( colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(8.dp),onClick = { onDismiss() }) {
                Text("No", color = Color.White)
            }
        }
    )
}