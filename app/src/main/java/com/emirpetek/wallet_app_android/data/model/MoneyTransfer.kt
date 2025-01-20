package com.emirpetek.wallet_app_android.data.model

import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import java.math.BigDecimal

data class MoneyTransfer(
    val senderId: Long,
    val senderIbanNumber: String,
    val receiverIban: String,
    val amount: BigDecimal,
    val currencyType: CurrencyType,
    val timestamp: Long,
    val description: String? = null
)
