package com.emirpetek.wallet_app_android.data.request

import java.math.BigDecimal

data class WithdrawMoneyRequest(
    val userID:Long,
    val amount: BigDecimal,
    val cardID:Long
)