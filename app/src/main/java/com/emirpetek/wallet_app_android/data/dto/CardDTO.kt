package com.emirpetek.wallet_app_android.data.dto

import com.emirpetek.wallet_app_android.data.model.enum.CardType
import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import java.math.BigDecimal

data class CardDTO(
    val id: Long,
    var cardNumber: String,
    var cardHolder: String,
    var expireDate: String,
    var cvv: Int,
    var cardType: CardType,
    var userID: Long,
    var accountID: String,
    val balance: BigDecimal,
    val currency: CurrencyType
)
