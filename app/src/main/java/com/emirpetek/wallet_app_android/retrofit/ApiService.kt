package com.emirpetek.wallet_app_android.retrofit

import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.model.enum.MoneyTransferReturnStatements
import com.emirpetek.wallet_app_android.data.model.enum.PasswordResponse
import com.emirpetek.wallet_app_android.data.request.CreateCardRequest
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.LoadBalanceRequest
import com.emirpetek.wallet_app_android.data.request.LoginRequest
import com.emirpetek.wallet_app_android.data.request.RegisterRequest
import com.emirpetek.wallet_app_android.data.request.MoneyTransferRequest
import com.emirpetek.wallet_app_android.data.request.PasswordChangeRequest
import com.emirpetek.wallet_app_android.data.request.WithdrawMoneyRequest
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

    @GET("user/isEmailExist/{email}")
    suspend fun isEmailExist(@Path("email") email: String) : Response<Boolean>

    @GET("user/getUserIdFromEmail/{email}")
    suspend fun getUserIdFromEmail(@Path("email") email: String): Response<Long>

    @POST("card/getCard")
    suspend fun getUserCards(@Body getCardRequest: GetCardRequest): Response<List<CardDTO>>

    @POST("card/createCard")
    suspend fun createCard(@Body createCardRequest: CreateCardRequest): Response<Boolean>

    @POST("transaction/moneyTransfer")
    suspend fun transferMoney(@Body moneyTransferRequest: MoneyTransferRequest): Response<MoneyTransferReturnStatements>

    @GET("transaction/getTransactions/{userID}")
    suspend fun getTransactions(@Path("userID") userID: Long) : Response<List<Transaction>>

    @POST("card/loadBalance")
    suspend fun loadBalance(@Body loadBalanceRequest: LoadBalanceRequest): Response<Boolean>

    @POST("card/withdrawMoney")
    suspend fun withdrawMoney(@Body withdrawMoneyRequest: WithdrawMoneyRequest): Response<Boolean>

    @GET("transaction/payBill/{userID}")
    suspend fun payBill(@Path("userID") userID: Long): Response<Boolean>

    @GET("transaction/randomPayment/{userID}")
    suspend fun randomPayment(@Path("userID") userID: Long): Response<Boolean>

    @GET("transaction/getNumberOfTransactions/{userID}")
    suspend fun getNumberOfTransactions(@Path("userID") userID: Long): Response<Int>

    @GET("card/getNumberOfCards/{userID}")
    suspend fun getNumberOfCards(@Path("userID") userID: Long): Response<Int>

    @POST("user/changePassword")
    suspend fun changePassword(@Body passwordChangeRequest: PasswordChangeRequest): Response<PasswordResponse>


}