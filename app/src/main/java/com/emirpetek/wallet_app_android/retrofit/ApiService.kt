package com.emirpetek.wallet_app_android.retrofit

import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.request.LoginRequest
import com.emirpetek.wallet_app_android.data.request.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("auth/login")
    suspend fun loginWithEmailAndPassword(@Body loginRequest: LoginRequest): Response<UserDTO>

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<Boolean>

    @GET("user/{userID}")
    suspend fun getUserData(@Path("userID") userID:Long): Response<UserDTO>


}