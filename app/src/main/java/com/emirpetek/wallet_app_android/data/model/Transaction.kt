package com.emirpetek.wallet_app_android.data.model

import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import com.emirpetek.wallet_app_android.data.model.enum.TransactionDirection
import com.emirpetek.wallet_app_android.data.model.enum.TransactionType
import java.math.BigDecimal

data class Transaction(
    val id: Long? = null, // ID (Veritabanı tarafından otomatik oluşturulur)
    val userId: Long, // İşlemi yapan kullanıcı ID'si
    val transactionType: TransactionType, // İşlem türü (enum)
    val transactionDirection: TransactionDirection,
    val amount: BigDecimal, // İşlem tutarı
    val currency: CurrencyType, // Para birimi (enum)
    val transactionDate: Long, // İşlem tarihi (timestamp)
    val description: String? = null // İşlem açıklaması (opsiyonel)
)