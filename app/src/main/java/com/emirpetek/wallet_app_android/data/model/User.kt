package com.emirpetek.wallet_app_android.data.model

import com.google.gson.annotations.SerializedName

data class User(

//    @SerializedName("id")
    val id: Long? = null,
 //   @SerializedName("firstName")
    val firstName: String,
   // @SerializedName("lastName")
    val lastName: String,
    //@SerializedName("email")
    val email:String,
    //@SerializedName("password")
    val password:String,
    //@SerializedName("birthdate")
    val birthdate:String,
    //@SerializedName("createdAt")
    val createdAt:Long
)