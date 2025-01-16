package com.emirpetek.wallet_app_android.data.request

import com.emirpetek.wallet_app_android.data.model.enum.CardType
import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import java.math.BigDecimal

data class CreateCardRequest(
    val cardType: CardType? = null,
    val cardHolder: String? = null,
    val userID: Long? = null,
    val balance: BigDecimal = BigDecimal.ZERO,
    val currencyType: CurrencyType? = null
)
