package com.emirpetek.wallet_app_android.repository

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



}