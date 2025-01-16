package com.emirpetek.wallet_app_android.ui.viewmodel.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.repository.CardRepository
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val userRepository = UserRepository(RetrofitClient.instance)
    private val cardRepository = CardRepository(RetrofitClient.instance)
    val cardsResult = MutableLiveData<Result<List<CardDTO>>>()

    fun getUserID(mContext:Context) : Long{
        return userRepository.getUserIdFromSP(mContext)
    }

    fun getUserCards(getCardRequest: GetCardRequest){
        viewModelScope.launch {
            val result = cardRepository.getUserCards(getCardRequest)
            cardsResult.value = result
        }
    }



}