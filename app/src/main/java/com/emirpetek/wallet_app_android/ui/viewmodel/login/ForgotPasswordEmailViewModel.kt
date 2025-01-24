package com.emirpetek.wallet_app_android.ui.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class ForgotPasswordEmailViewModel : ViewModel() {

    val userRepository = UserRepository(RetrofitClient.instance)
    val isEmailExist = MutableLiveData<Result<Boolean>>()
    val userID = MutableLiveData<Result<Long>>()


    fun isEmailExist(email: String){
        viewModelScope.launch {
            isEmailExist.value = userRepository.isEmailExist(email)
        }
    }

    fun getUserIdFromEmail(email: String){
        viewModelScope.launch {
            userID.value = userRepository.getUserIdFromEmail(email)
        }
    }



}