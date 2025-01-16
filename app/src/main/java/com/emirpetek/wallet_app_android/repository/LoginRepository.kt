package com.emirpetek.wallet_app_android.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.request.LoginRequest
import com.emirpetek.wallet_app_android.data.request.RegisterRequest
import com.emirpetek.wallet_app_android.retrofit.ApiService
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val apiService: ApiService) {

    private fun getSharedPreferences(mContext: Context): SharedPreferences {
        return mContext.getSharedPreferences("LoginPreferences", Context.MODE_PRIVATE)
    }

    fun saveRememberMe(rememberMe:Boolean,mContext: Context){
        getSharedPreferences(mContext).edit().putBoolean("rememberMe",rememberMe).apply()
    }



    suspend fun login(email: String, password: String): Result<UserDTO> {
        return try {
            val response = apiService.loginWithEmailAndPassword(LoginRequest(email, password))

            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    Result.success(user)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e) // Ağ hatalarını yakala
        }
    }


    suspend fun registerUser(registerRequest: RegisterRequest): Result<Boolean>{
        return try {
            val response = apiService.registerUser(registerRequest)
            if (response.isSuccessful) {
                val result = response.body() // Backend'den gelen true/false değeri
//                Log.e("repo body:", result.toString())
                if (result != null) {
                    Result.success(result)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            } else {
                Result.failure(Exception(response.message()))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }



}