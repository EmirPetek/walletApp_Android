package com.emirpetek.wallet_app_android.ui.viewmodel.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.LoadBalanceRequest
import com.emirpetek.wallet_app_android.data.request.WithdrawMoneyRequest
import com.emirpetek.wallet_app_android.repository.CardRepository
import com.emirpetek.wallet_app_android.repository.TransactionRepository
import com.emirpetek.wallet_app_android.repository.UserRepository
import com.emirpetek.wallet_app_android.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val userRepository = UserRepository(RetrofitClient.instance)
    private val cardRepository = CardRepository(RetrofitClient.instance)
    private val transactionRepository = TransactionRepository(RetrofitClient.instance)
    val cardsResult = MutableLiveData<Result<List<CardDTO>>>()
    val loadBalanceResult = MutableLiveData<Result<Boolean>>()
    val withdrawMoneyResult = MutableLiveData<Result<Boolean>>()
    val payBillResult = MutableLiveData<Result<Boolean>>()
    val transactions = MutableLiveData<Result<List<Transaction>>>()
    val fullname = MutableLiveData<Result<String>>()

    fun getUserID(mContext:Context) : Long{
        return userRepository.getUserIdFromSP(mContext)
    }

    fun getUserCards(getCardRequest: GetCardRequest){
        viewModelScope.launch {
            val result = cardRepository.getUserCards(getCardRequest)
            cardsResult.value = result
        }
    }

    fun getUserFullname(userID: Long){
        viewModelScope.launch {
            val result = userRepository.getUserFullName(userID)
            fullname.value = result
        }
    }

    fun getTransactions(userID: Long){
        viewModelScope.launch {
            val result = transactionRepository.getTransactions(userID)
            transactions.value = result
        }
    }


    fun loadBalance(loadBalanceRequest: LoadBalanceRequest){
        viewModelScope.launch {
            val result = cardRepository.loadBalance(loadBalanceRequest)
            loadBalanceResult.value = result
        }
    }

    fun withdrawMoney(withdrawMoneyRequest: WithdrawMoneyRequest){
        viewModelScope.launch {
            val result = cardRepository.withdrawMoney(withdrawMoneyRequest)
            withdrawMoneyResult.value = result
        }
    }

    fun payBill(userID: Long){
        viewModelScope.launch {
            val result = transactionRepository.payBill(userID)
            payBillResult.value = result
        }
    }




}