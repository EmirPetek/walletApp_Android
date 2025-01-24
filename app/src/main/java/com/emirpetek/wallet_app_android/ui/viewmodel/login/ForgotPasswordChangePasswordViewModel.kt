package com.emirpetek.wallet_app_android.ui.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.model.enum.PasswordResponse
import com.emirpetek.wallet_app_android.data.request.PasswordChangeRequest
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class ForgotPasswordChangePasswordViewModel : ViewModel() {

    val userRepository = UserRepository(RetrofitClient.instance)
    val passwordChangeResult = MutableLiveData<Result<PasswordResponse>>()


    fun changePassword(passwordChangeRequest: PasswordChangeRequest){
        viewModelScope.launch {
            passwordChangeResult.value = userRepository.changePassword(passwordChangeRequest)
        }
    }


}