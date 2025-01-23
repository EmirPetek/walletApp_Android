package com.emirpetek.wallet_app_android.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.model.enum.PasswordResponse
import com.emirpetek.wallet_app_android.data.request.PasswordChangeRequest
import com.emirpetek.wallet_app_android.retrofit.ApiService
import com.google.gson.Gson
import org.json.JSONObject

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

    fun saveUserToSP(userDTO: UserDTO,mContext: Context){
        return getSharedPreferences(mContext).edit().putString("userDTO",Gson().toJson(userDTO)).apply()
    }

    fun getUserDTO(mContext: Context): UserDTO{
        return Gson().fromJson(getSharedPreferences(mContext).getString("userDTO",""),UserDTO::class.java)
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

    suspend fun getUserFullName(userID: Long): Result<String> {
        return try {
            val response = apiService.getFullNameFromUserID(userID)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failure in getting fullname process."))
            }else Result.failure(Exception("exception mesajı: " + response.message()))
        }catch (e: Exception){
            return Result.failure(e)
        }
    }


    suspend fun changePassword(passwordChangeRequest: PasswordChangeRequest): Result<PasswordResponse>{
        return try {
            val response = apiService.changePassword(passwordChangeRequest).body()

            if (response != null){
                Result.success(response)
            }else{
                Result.failure(Exception("Password could not change"))
            }
        }catch (e: Exception){
            return Result.failure(e)
        }
    }









}