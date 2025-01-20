package com.emirpetek.wallet_app_android.data.request

import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import java.math.BigDecimal

data class MoneyTransferRequest(
    val senderID: Long,
    val senderIbanNumber: String,
    val receiverIbanNumber: String,
    val amount: BigDecimal,
    val currencyType: CurrencyType,
    val description: String
)
