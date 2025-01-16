package com.emirpetek.wallet_app_android.repository

import android.content.Context
import android.content.SharedPreferences
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.retrofit.ApiService

class UserRepository(private val apiService: ApiService) {

    private fun getSharedPreferences(mContext:Context): SharedPreferences{
        return mContext.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
    }

    fun saveUserIdToSP(userID:Long,mContext: Context){
        getSharedPreferences(mContext).edit().putLong("userID",userID).apply()
    }

    fun getUserIdFromSP(mContext: Context): Long{
        return getSharedPreferences(mContext).getLong("userID",0L)
    }

    suspend fun getUserData(userID: Long): Result<UserDTO> {
        return try {
            val response = apiService.getUserData(userID)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("User data could not get."))
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }





}