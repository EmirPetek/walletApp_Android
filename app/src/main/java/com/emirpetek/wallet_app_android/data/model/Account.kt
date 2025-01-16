package com.emirpetek.wallet_app_android.data.model

import java.math.BigDecimal

data class Account(
    val id: Int,
    val accountNumber: String,
    val balance:BigDecimal = BigDecimal.ZERO,
    val currency:String = "TRY",
    val userID: Int,
    val createdAt: Long = System.currentTimeMillis()

)
