package com.emirpetek.wallet_app_android.data.model

import com.emirpetek.wallet_app_android.data.model.enum.CardType
import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import java.math.BigDecimal

data class Card(
    val id: Long,
    val cardNumber: String,
    val cardHolder: String,
    val expireDate: String,
    val cvv: Int,
    val cardType: CardType,
    val userID: Long,
    val createdAt: Long,
    val accountID: String,
    val balance: BigDecimal,
    val currency: CurrencyType
)
