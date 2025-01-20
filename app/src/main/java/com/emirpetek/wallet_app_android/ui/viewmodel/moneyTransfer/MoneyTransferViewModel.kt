package com.emirpetek.wallet_app_android.ui.viewmodel.moneyTransfer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.model.enum.MoneyTransferReturnStatements
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.MoneyTransferRequest
import com.emirpetek.wallet_app_android.repository.CardRepository
import com.emirpetek.wallet_app_android.repository.TransactionRepository
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class MoneyTransferViewModel : ViewModel() {


    private val userRepository = UserRepository(RetrofitClient.instance)
    private val cardRepository = CardRepository(RetrofitClient.instance)
    private val transactionRepository = TransactionRepository(RetrofitClient.instance)
    val cardsResult = MutableLiveData<Result<List<CardDTO>>>()
    val transferResult = MutableLiveData<Result<MoneyTransferReturnStatements>>()

    fun getUserID(mContext: Context) : Long{
        return userRepository.getUserIdFromSP(mContext)
    }

    fun getUserCards(getCardRequest: GetCardRequest){
        viewModelScope.launch {
            val result = cardRepository.getUserCards(getCardRequest)
            cardsResult.value = result
        }
    }

    fun transferMoney(moneyTransferRequest: MoneyTransferRequest){
        viewModelScope.launch {
            val result = transactionRepository.transferMoney(moneyTransferRequest)
            transferResult.value = result
        }
    }


}