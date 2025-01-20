package com.emirpetek.wallet_app_android.data.model.enum

import com.emirpetek.wallet_app_android.R

enum class TransactionType(val stringResId: Int) {
    PAYMENT(R.string.transaction_type_payment),
    TRANSFER(R.string.transaction_type_transfer),
    DEPOSIT(R.string.transaction_type_deposit),
    WITHDRAWAL(R.string.transaction_type_withdrawal),
    BILL_PAYMENT(R.string.transaction_type_bill_payment)
}

