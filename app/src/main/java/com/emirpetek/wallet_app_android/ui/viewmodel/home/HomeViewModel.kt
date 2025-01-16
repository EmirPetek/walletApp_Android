package com.emirpetek.wallet_app_android.ui.viewmodel.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient

class HomeViewModel : ViewModel() {

    private val userRepository = UserRepository(RetrofitClient.instance)

    fun getUserID(mContext:Context) : Long{
        return userRepository.getUserIdFromSP(mContext)
    }



}