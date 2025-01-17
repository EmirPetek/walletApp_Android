package com.emirpetek.wallet_app_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.enum.CardType
import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType

class AddCardFragmentCardCurrencyAdapter(val mContext: Context,
val cardList: List<CurrencyType>
): RecyclerView.Adapter<AddCardFragmentCardCurrencyAdapter.CardHolder>() {

    lateinit var selectedCurrencyType: CurrencyType
    var anySelected = false
    var selectedPosition = -1

    interface OnItemClickListener {
        fun onItemClicked(selectedItemCurrency: CurrencyType)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class CardHolder(view: View): RecyclerView.ViewHolder(view){
        val currencyText: TextView = view.findViewById(R.id.textViewCardCurrency)
        val cardView: CardView = view.findViewById(R.id.cardviewCurrency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_currency,parent,false))
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val type = cardList[position]

        var cardCurrencyType: String = ""
        when(type){
            CurrencyType.TRY -> {
                cardCurrencyType = mContext.getString(R.string.currency_try_tl)
            }
            CurrencyType.EUR -> {
                cardCurrencyType = mContext.getString(R.string.currency_eur_euro)
            }
            CurrencyType.USD -> {
                cardCurrencyType = mContext.getString(R.string.currency_usd_dollar)
            }
            else -> {
                cardCurrencyType = mContext.getString(R.string.currency_try_tl)
            }
        }

        holder.currencyText.setText(cardCurrencyType)


        // Eğer bu pozisyon seçili ise arka plan rengini değiştir
        if (selectedPosition == position) {
            holder.cardView.setCardBackgroundColor(mContext.getColor(R.color.selected_card)) // Seçili arka plan rengi
            holder.currencyText.setTextColor(mContext.getColor(R.color.bg_white_2))
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getColor(R.color.bg_white_2)) // Varsayılan arka plan rengi
            holder.currencyText.setTextColor(mContext.getColor(R.color.selected_card))
        }

        // Tıklama olayını ayarla
        holder.cardView.setOnClickListener {
            // Eski seçimin rengini varsayılan renge döndür
            notifyItemChanged(selectedPosition)

            // Yeni seçimi belirle
            selectedPosition = holder.adapterPosition
            selectedCurrencyType = type // Seçilen CardType değişkene atanır

            // Yeni seçimin rengini güncelle
            notifyItemChanged(selectedPosition)

            // İsteğe bağlı: Listener ile seçilen CardType'ı üst katmana ilet
            listener?.onItemClicked(selectedCurrencyType)
        }



    }

}