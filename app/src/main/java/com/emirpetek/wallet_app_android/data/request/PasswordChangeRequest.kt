package com.emirpetek.wallet_app_android.data.request

data class PasswordChangeRequest(
    val userID: Long,
    val oldPassword:String?= null,
    val newPassword: String?= null
)
