package com.emirpetek.wallet_app_android.data.dto

import java.math.BigDecimal

data class AccountDTO(
    val id: Int,
    val accountNumber: String,
    val balance: BigDecimal = BigDecimal.ZERO,
    val currency:String = "TRY",
    val userID: Int,
)