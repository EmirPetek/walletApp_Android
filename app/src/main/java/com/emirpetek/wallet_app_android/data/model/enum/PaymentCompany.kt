package com.emirpetek.wallet_app_android.data.model.enum

import com.emirpetek.wallet_app_android.R

enum class PaymentCompany(val iconResId: Int) {
    TRENDYOL(R.drawable.ic_trendyol),
    HEPSIBURADA(R.drawable.ic_hepsiburada),
    N11(R.drawable.ic_n11),
    AMAZON(R.drawable.ic_amazon),
    EBAY(R.drawable.ic_ebay),
    ALIEXPRESS(R.drawable.ic_aliexpress),
    ETSY(R.drawable.ic_etsy),
    WALMART(R.drawable.ic_walmart),
    WAYFAIR(R.drawable.ic_wayfair),
    SHEIN(R.drawable.ic_shein),
    ZARA(R.drawable.ic_zara),
    HM(R.drawable.ic_hm),
    PULLANDBEAR(R.drawable.ic_pb),
    ALIBABA(R.drawable.ic_alibaba),
    IKEA(R.drawable.ic_ikea),
    BOOKING_COM(R.drawable.ic_booking_com),
    AIRBNB(R.drawable.ic_airbnb),
    YEMEKSEPETI(R.drawable.ic_yemeksepeti);


    companion object {
        fun fromDescription(description: String): PaymentCompany? {
            return values().find { it.name.equals(description, ignoreCase = true) }
        }
    }




}
