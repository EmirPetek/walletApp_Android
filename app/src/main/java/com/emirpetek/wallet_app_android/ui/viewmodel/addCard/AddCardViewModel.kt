package com.emirpetek.wallet_app_android.ui.viewmodel.addCard

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.request.CreateCardRequest
import com.emirpetek.wallet_app_android.repository.CardRepository
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class AddCardViewModel : ViewModel() {
    private val cardRepository = CardRepository(RetrofitClient.instance)
    private val userRepository = UserRepository(RetrofitClient.instance)
    val createCardResult : MutableLiveData<Result<Boolean>> = MutableLiveData()

    fun getUserID(mContext: Context) : Long{
        return userRepository.getUserIdFromSP(mContext)
    }

    suspend fun createCard(createCardRequest: CreateCardRequest){
        createCardResult.value = cardRepository.createCard(createCardRequest)
    }


}