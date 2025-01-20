package com.emirpetek.wallet_app_android.repository

import android.util.Log
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.model.enum.MoneyTransferReturnStatements
import com.emirpetek.wallet_app_android.data.request.MoneyTransferRequest
import com.emirpetek.wallet_app_android.retrofit.ApiService

class TransactionRepository(private val apiService: ApiService) {

    suspend fun transferMoney(moneyTransferRequest: MoneyTransferRequest): Result<MoneyTransferReturnStatements>{
        return try {
            val response = apiService.transferMoney(moneyTransferRequest)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failure in transfer process."))
            }else Result.failure(Exception(response.message()))
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

    suspend fun getTransactions(userID : Long): Result<List<Transaction>>{
        return try {
            val response = apiService.getTransactions(userID)
            if (response.isSuccessful){
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failure in getting transaction process."))
            }else Result.failure(Exception(response.message()))
        }catch (e: Exception){
            return Result.failure(e)
        }
    }








}