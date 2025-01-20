package com.emirpetek.wallet_app_android.data.request

import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import com.emirpetek.wallet_app_android.data.model.enum.TransactionType
import java.math.BigDecimal

data class CreateTransactionRequest(
    val userId: Long,
    val transactionType: TransactionType,
    val amount: BigDecimal,
    val currency: CurrencyType,
    val transactionDate: Long,
    val description: String? = null,
    val transactionCardId: Long
) {
}