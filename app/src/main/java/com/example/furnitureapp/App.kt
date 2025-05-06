package com.example.furnitureapp

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.furnitureapp.model.CartViewModel
import com.example.furnitureapp.model.FavouriteViewModel
import com.example.furnitureapp.model.UserViewModel
import com.example.furnitureapp.screens.CartScreen
import com.example.furnitureapp.screens.CheckoutScreen
import com.example.furnitureapp.screens.CongratulationScreen
import com.example.furnitureapp.screens.DetailProductScreen
import com.example.furnitureapp.screens.LoginScreen
import com.example.furnitureapp.screens.MainScreen
import com.example.furnitureapp.screens.ProfileScreen
import com.example.furnitureapp.screens.RegisterScreen
import com.example.furnitureapp.screens.SearchScreen
import com.example.furnitureapp.screens.WelcomeScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun MyApp(googleSignInClient: GoogleSignInClient){
    val navController=rememberNavController()
    val cartViewModel: CartViewModel = viewModel()
    val favouriteViewModel: FavouriteViewModel = viewModel()
    val userViewModel:UserViewModel= viewModel()
    NavHost(navController = navController, startDestination = "welcome"){
        composable("welcome"){ WelcomeScreen(navController = navController) }
        composable("login"){ LoginScreen(navController = navController,userViewModel=userViewModel) }
        composable("register"){ RegisterScreen(navController = navController) }
        composable("main") { MainScreen(navController = navController,googleSignInClient = googleSignInClient ,userViewModel=userViewModel,favouriteViewModel=favouriteViewModel) }
        composable("detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            // Call your detail screen composable passing the productId
            DetailProductScreen(productId = productId,navController,favouriteViewModel=favouriteViewModel, cartViewModel = cartViewModel)
        }
        composable("cart") { CartScreen(navController = navController, cartViewModel = cartViewModel) }
        composable("checkout/{totalPrice}") { backStackEntry ->
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toDouble() ?: 0.0
            CheckoutScreen(navController,userViewModel, totalPrice)
        }
        composable("congratulation"){ CongratulationScreen(navController = navController,cartViewModel=cartViewModel)}
        composable("search"){ SearchScreen(navController = navController) }
        composable("profile") {
            ProfileScreen(
                navController = navController,
                userViewModel = userViewModel,
                googleSignInClient = googleSignInClient
            )
        }
    }
}
