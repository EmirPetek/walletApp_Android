package com.emirpetek.wallet_app_android.ui.viewmodel.profile

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.model.enum.PasswordResponse
import com.emirpetek.wallet_app_android.data.request.PasswordChangeRequest
import com.emirpetek.wallet_app_android.repository.CardRepository
import com.emirpetek.wallet_app_android.repository.TransactionRepository
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val userRepository = UserRepository(RetrofitClient.instance)
    private val cardRepository = CardRepository(RetrofitClient.instance)
    private val transactionRepository = TransactionRepository(RetrofitClient.instance)
    val user = MutableLiveData<Result<UserDTO>>()
    val passwordChangeResult = MutableLiveData<Result<PasswordResponse>>()

    val numOfCards = MutableLiveData<Result<Int>>()
    val numOfTransactions = MutableLiveData<Result<Int>>()



    fun getUserID(mContext: Context) : Long {
        return userRepository.getUserIdFromSP(mContext)
    }

    fun getUserInfo(userID: Long){
        viewModelScope.launch {
            val result = userRepository.getUserData(userID)
            user.value = result
        }
    }

    fun getNumOfCards(userID: Long){
        viewModelScope.launch {
            numOfCards.value = cardRepository.getNumberOfCards(userID)
        }
    }

    fun getNumOfTransactions(userID: Long){
        viewModelScope.launch {
            numOfTransactions.value = transactionRepository.getNumberOfTransactions(userID)
        }
    }


    fun changePassword(passwordChangeRequest: PasswordChangeRequest){
        viewModelScope.launch {
            passwordChangeResult.value = userRepository.changePassword(passwordChangeRequest)
        }
    }




}