package com.example.furnitureapp.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.furnitureapp.api.HttpRequest
import com.example.furnitureapp.api.LoginRequest
import com.example.furnitureapp.api.UpdateUserRequest
import com.example.furnitureapp.api.User
import com.example.furnitureapp.api.UserRP
import com.example.furnitureapp.manager.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class UserViewModel : ViewModel() {
    private val apiService = HttpRequest.getInstance()
    val loggedInUser = MutableLiveData<UserRP?>()
    fun fetchUserInfo(userId: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getUserById(userId)
                if (response.status == 200) {
                    loggedInUser.value = response.data
                    Log.d("UserViewModel", "User loaded: ${response.data.name}")
                } else {
                    Log.e("UserViewModel", "Fetch failed: ${response.message}")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error: ${e.message}")
            }
        }
        }
        // Function to register a new user
        fun registerUser(
            name: String,
            email: String,
            password: String,
            onResult: (String?) -> Unit
        ) {
            viewModelScope.launch {
                try {
                    val response = apiService.registerUser(
                        User(
                            name,
                            email,
                            password
                        )
                    )
                    if (response.isSuccessful) {
                        onResult(null) // Registration successful, no error message
                    } else {
                        onResult("Registration failed: ${response.message()}") // Registration failed, return error message
                    }
                } catch (e: Exception) {
                    onResult("Registration failed: ${e.message}") // Exception occurred during registration
                }
            }
        }

        //    val loggedInUser = MutableLiveData<UserRP?>()
        // Function to login user
        fun loginUser(email: String, password: String, onResult: (String?) -> Unit) {
            viewModelScope.launch {
                try {
                    val response =
                        apiService.loginUser(loginRequest = LoginRequest(email, password))

                    if (response.status == 200) {
                        val id = response.id;
                        TokenManager.userId = id


//                    val user=response.body()
//                    loggedInUser.value=user
                        Log.d("TAG", "loginUser: " + response.id)

                        onResult(null) // Login successful, no error message
                    } else {
                        onResult("Login failed: ${response.message}") // Login failed, return error message
                    }
                } catch (e: Exception) {
                    onResult("Login failed: ${e.message}") // Exception occurred during login
                }
            }
        }

        fun loginWithGoogle(idToken: String, onResult: (String?) -> Unit) {
            viewModelScope.launch {
                try {
                    val body = mapOf("idToken" to idToken)
                    val response = apiService.loginWithGoogle(body)

                    if (response.status == 200) {
                        TokenManager.userId = response.data._id // Lưu userId nếu cần
                        // Nếu bạn có lưu token:
                        TokenManager.token = response.token

                        Log.d("GoogleLogin", "Login Google thành công: ${response.data.email}")
                        onResult(null)
                    } else {
                        onResult("Google login failed: ${response.message}")
                    }
                } catch (e: Exception) {
                    onResult("Google login failed: ${e.message}")
                }
            }
        }
    }

