package com.emirpetek.wallet_app_android.repository

import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
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

}