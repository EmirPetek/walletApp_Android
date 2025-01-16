package com.emirpetek.wallet_app_android.data.dto

data class UserDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email:String,
    val birthdate:String,
    val createdAt:Long
)