package com.emirpetek.wallet_app_android.ui.viewmodel.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.dto.UserDTO
import com.emirpetek.wallet_app_android.data.request.LoginRequest
import com.emirpetek.wallet_app_android.repository.LoginRepository
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = LoginRepository(RetrofitClient.instance)
    private val userRepository = UserRepository(RetrofitClient.instance)
    val loginResult = MutableLiveData<Result<UserDTO>>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.login(email, password)
            loginResult.postValue(result)
        }
    }

    fun saveUserIdToSP(userID:Long,mContext:Context){
        userRepository.saveUserIdToSP(userID,mContext)
    }


}