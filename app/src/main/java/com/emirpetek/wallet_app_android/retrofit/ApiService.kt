package com.emirpetek.wallet_app_android.retrofit

import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.model.enum.MoneyTransferReturnStatements
import com.emirpetek.wallet_app_android.data.request.CreateCardRequest
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.LoginRequest
import com.emirpetek.wallet_app_android.data.request.RegisterRequest
import com.emirpetek.wallet_app_android.data.request.MoneyTransferRequest
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

    @GET("user/getFullName/{userID}")
    suspend fun getFullNameFromUserID(@Path("userID") userID: Long): Response<String>

    @POST("card/getCard")
    suspend fun getUserCards(@Body getCardRequest: GetCardRequest): Response<List<CardDTO>>

    @POST("card/createCard")
    suspend fun createCard(@Body createCardRequest: CreateCardRequest): Response<Boolean>

    @POST("transaction/moneyTransfer")
    suspend fun transferMoney(@Body moneyTransferRequest: MoneyTransferRequest): Response<MoneyTransferReturnStatements>

    @GET("transaction/getTransactions/{userID}")
    suspend fun getTransactions(@Path("userID") userID: Long) : Response<List<Transaction>>



}