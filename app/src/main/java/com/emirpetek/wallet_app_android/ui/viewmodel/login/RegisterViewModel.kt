package com.emirpetek.wallet_app_android.ui.viewmodel.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.request.RegisterRequest
import com.emirpetek.wallet_app_android.repository.LoginRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    val loginRepo = LoginRepository(RetrofitClient.instance)
    val registerResponse = MutableLiveData<Result<Boolean>?>()


    suspend fun registerUser(registerRequest: RegisterRequest){
        //Log.e("viewmodel bişeyi öncesi: ", registerResponse.value.toString())
            val result = loginRepo.registerUser(registerRequest)
            registerResponse.value = result
            //Log.e("viewmodel result ", result.toString())
            //Log.e("viewmodel registerResponse ", registerResponse.value.toString())

    }
}