package com.emirpetek.wallet_app_android.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeDateConversion {

    fun formatTimestamp(timestamp: Long): String {
        // Eğer timestamp milisaniye cinsindeyse, 100 yıl sonrası kontrolü ile karar verilir.
        val adjustedTimestamp = if (timestamp > 1_000_000_000_000L) {
            timestamp // Milisaniye cinsinden
        } else {
            timestamp * 1000 // Saniye cinsinden
        }

        // Tarihi GG/AA/YYYY HH:MM formatına dönüştür
        val date = Date(adjustedTimestamp)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}