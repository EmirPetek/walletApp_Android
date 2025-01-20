package com.emirpetek.wallet_app_android.data.model.enum

enum class MoneyTransferReturnStatements {
    SUCCESSFUL_TRANSFER,
    FAILURE_AMOUNT_LOWER_THAN_ZERO,
    FAILURE_INVALID_IBAN_NUMBER,
    FAILURE_DIFFERENT_CURRENCY,
    FAILURE_SERVER
}