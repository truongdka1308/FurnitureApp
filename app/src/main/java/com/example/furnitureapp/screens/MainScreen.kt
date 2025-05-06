package com.example.furnitureapp.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.furnitureapp.R
import com.example.furnitureapp.model.FavouriteViewModel
import com.example.furnitureapp.model.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController,googleSignInClient: GoogleSignInClient, userViewModel: UserViewModel= viewModel(), favouriteViewModel: FavouriteViewModel = viewModel()) {
    val bottomNavController = rememberNavController()
    var selectedTab by remember { mutableStateOf(0) }
//    val loggedInUser = userViewModel.loggedInUser.observeAsState()
//    Log.d("TAG", "MainScreen: "+loggedInUser)
    // Function to update selected tab based on current destination
    fun updateSelectedTab(destination: NavDestination?) {
        selectedTab = when (destination?.route) {
            "home" -> 0
            "favorite" -> 1
            "notification" -> 2
            "profile" -> 3
            else -> selectedTab
        }
    }

    // Observe navigation state and update selected tab
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry) {
        updateSelectedTab(navBackStackEntry?.destination)
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White
            ) {
                BottomNavigationItem(
                    selected = selectedTab == 0,
                    onClick = {
                        if (selectedTab != 0) {
                            selectedTab = 0
                            bottomNavController.navigate("home")
                        }
                    },
                    icon = {
                        Icon(
                            if (selectedTab == 0) Icons.Filled.Home else Icons.Outlined.Home,
                            contentDescription = "Home"

                        )
                    },


                )
                BottomNavigationItem(
                    selected = selectedTab == 1,
                    onClick = {
                        if (selectedTab != 1) {
                            selectedTab = 1
                            bottomNavController.navigate("favorite")
                        }
                    },
                    icon = {
                        Image(
                            if (selectedTab == 1) painterResource(id = R.drawable.fillbookmark) else painterResource(id = R.drawable.outbookmark),
                            contentDescription = "Favorite",
                            modifier = Modifier.size(20.dp)
                        )
                    },

                )
                BottomNavigationItem(
                    selected = selectedTab == 2,
                    onClick = {
                        if (selectedTab != 2) {
                            selectedTab = 2
                            bottomNavController.navigate("notification")
                        }
                    },
                    icon = {
                        Icon(
                            if (selectedTab == 2) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                            contentDescription = "Notification"
                        )
                    },

                )
                BottomNavigationItem(
                    selected = selectedTab == 3,
                    onClick = {
                        if (selectedTab != 3) {
                            selectedTab = 3
                            bottomNavController.navigate("profile")
                        }
                    },
                    icon = {
                        Icon(
                            if (selectedTab == 3) Icons.Filled.Person else Icons.Outlined.Person,
                            contentDescription = "Profile"
                        )
                    },

                )
            }
        }
    ) {
        NavHost(navController = bottomNavController, startDestination = "home") {
            composable("home") { HomeScreen(navController = navController) }
            composable("favorite") { FavouriteScreen(navController = navController, favouriteViewModel = favouriteViewModel) }
            composable("notification") { NotificationScreen() }
            composable("profile") { ProfileScreen(navController=navController, googleSignInClient = googleSignInClient,userViewModel=userViewModel) }
        }
    }
}
