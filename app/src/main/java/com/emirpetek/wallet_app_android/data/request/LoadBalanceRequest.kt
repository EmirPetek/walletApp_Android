package com.emirpetek.wallet_app_android.data.request

import java.math.BigDecimal

data class LoadBalanceRequest(
    val userID:Long,
    val amount: BigDecimal,
    val cardID:Long
)