package com.emirpetek.wallet_app_android.repository

import android.util.Log
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.request.CreateCardRequest
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.LoadBalanceRequest
import com.emirpetek.wallet_app_android.data.request.WithdrawMoneyRequest
import com.emirpetek.wallet_app_android.retrofit.ApiService

class CardRepository(private val apiService: ApiService) {

    suspend fun getUserCards(getCardRequest: GetCardRequest): Result<List<CardDTO>>{
        return try {
            val response = apiService.getUserCards(getCardRequest)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Card data could not get."))
            }else{
                Result.failure(Exception(response.message()))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun createCard(createCardRequest: CreateCardRequest): Result<Boolean>{

        return try {
            val response = apiService.createCard(createCardRequest)
            if (response.isSuccessful) {
                val result = response.body() // Backend'den gelen true/false değeri
                Log.e("repo body:", result.toString())
                if (result != null) {
                    Result.success(result)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                Result.failure(Exception(response.message()))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }


    suspend fun loadBalance(loadBalanceRequest: LoadBalanceRequest): Result<Boolean>{

        return try {
            val response = apiService.loadBalance(loadBalanceRequest)
            if (response.isSuccessful) {
                val result = response.body() // Backend'den gelen true/false değeri
                Log.e("repo body:", result.toString())
                if (result != null) {
                    Result.success(result)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                Result.failure(Exception(response.message()))
            }
        }catch (e: Exception){
            Result.failure(e)
        }

    }

    suspend fun withdrawMoney(withdrawMoneyRequest: WithdrawMoneyRequest): Result<Boolean>{

        return try {
            val response = apiService.withdrawMoney(withdrawMoneyRequest)
            if (response.isSuccessful) {
                val result = response.body() // Backend'den gelen true/false değeri
                Log.e("repo body:", result.toString())
                if (result != null) {
                    Result.success(result)
                } else {
                    Result.failure(Exception("Response body is null"))
                }
            }else{
                Result.failure(Exception(response.message()))
            }
        }catch (e: Exception){
            Result.failure(e)
        }

    }


    suspend fun getNumberOfCards(userID: Long): Result<Int>{
        return try {
            val response = apiService.getNumberOfCards(userID)
            if (response.isSuccessful){
                val result = response.body()
                if (result != null) Result.success(result)
                else Result.failure(Exception("Response body is null"))
            }else{
                Result.failure(Exception(response.message()))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }




}