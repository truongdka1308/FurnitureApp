package com.example.furnitureapp.api

import com.example.furnitureapp.Category
import com.example.furnitureapp.model.Favourite.Favourite
import com.example.furnitureapp.model.Product.Product
import com.example.furnitureapp.model.User.GoogleLoginResponse
import com.example.furnitureapp.model.User.LoginResponse
import com.example.furnitureapp.response.ApiResponse
import com.example.furnitureapp.response.CategoryResponse
import com.example.furnitureapp.response.ProductResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class User(val name: String,val email:String,val password:String)
data class UserRP(val _id:String,val name: String,val email:String,val password:String,val avatarUrl:String,val address:String)

data class LoginRequest(val email: String, val password: String)
data class UpdateUserRequest(val id:String,val email: String, val newName: String, val newPassword: String)
interface ApiService {
@GET("api/categories")
suspend fun getCategories(): CategoryResponse
@GET("api/products/category/{categoryId}")
suspend fun getProductsByCategory(@Path("categoryId") categoryId: String): ApiResponse<Product>
    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") productId: String): ApiResponse<Product>
    @GET("api/products")
    suspend fun getProducts(): ApiResponse<List<Product>>
    @POST("api/register")
    suspend fun registerUser(@Body user: User): Response<Unit>
    @POST("api/user/{userId}/add_favourites")
    suspend fun addToFavourite(
        @Path("userId") userId: String,
        @Body favouriteRequest: Favourite
    ): ApiResponse<Favourite>
    @GET("api/favourites/check")
    suspend fun checkFavourite(
        @Query("userId") userId: String,
        @Query("productId") productId: String
    ): ApiResponse<Favourite>
    @DELETE("api/delete_Favourite/{userId}/{productId}")
    suspend fun deleteFavourite(
        @Path("userId") userId: String,
        @Path("productId") productId: String
    ): ApiResponse<String>
    @GET("api/user/{userId}/favourites")
    suspend fun getFavourites(@Path("userId") userId: String): ApiResponse<List<Product>>

    // Đăng nhập người dùng
    @POST("api/login/google")
    suspend fun loginWithGoogle(@Body body: Map<String, String>): GoogleLoginResponse
    @POST("api/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse
    @POST("api/updateUser") // Thêm một endpoint mới để cập nhật thông tin người dùng
    suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Response<Unit>
    @GET("api/getuserbyid/{id}")
    suspend fun getUserById(@Path("id") id: String): ApiResponse<UserRP>

}

