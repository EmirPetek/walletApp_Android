package com.emirpetek.wallet_app_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.Card
import com.emirpetek.wallet_app_android.data.model.enum.CardType

class AddCardFragmentCardTypeAdapter(
    val mContext:Context,
    val cardList: List<CardType>
): RecyclerView.Adapter<AddCardFragmentCardTypeAdapter.CardHolder>() {

    lateinit var selectedCardType : CardType
    var anySelected = false
    var selectedPosition = -1
    var isFirstClicked = false

    interface OnItemClickListener {
        fun onItemClicked(selectedItemCardType: CardType)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class CardHolder(view: View): RecyclerView.ViewHolder(view){
        val cardImage: ImageView = view.findViewById(R.id.imageViewCardviewCardType)
        val cardView: CardView = view.findViewById(R.id.cardviewCardType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_card_type,parent,false))
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val type = cardList[position]

        var cardTypeLogoImg: Int = 0
        when(type){
            CardType.VISA -> {
                cardTypeLogoImg = R.drawable.visa_logo
            }
            CardType.MASTERCARD -> {
                cardTypeLogoImg = R.drawable.mastercard_logo
            }
            CardType.MAESTRO -> {
                cardTypeLogoImg = R.drawable.maestro_logo
            }
            CardType.AMERICAN_EXPRESS -> {
                cardTypeLogoImg = R.drawable.american_express_logo
            }
            CardType.UNIONPAY -> {
                cardTypeLogoImg = R.drawable.union_pay_logo
            }
            CardType.DINERS_CLUB -> {
                cardTypeLogoImg = R.drawable.diners_club_logo
            }
            else -> {
                cardTypeLogoImg = R.drawable.visa_logo
            }
        }

        Glide.with(mContext).load(cardTypeLogoImg).into(holder.cardImage)



        // Eğer bu pozisyon seçili ise arka plan rengini değiştir
        if (selectedPosition == position) {
            holder.cardView.setCardBackgroundColor(mContext.getColor(R.color.selected_card)) // Seçili arka plan rengi
        } else {
            holder.cardView.setCardBackgroundColor(mContext.getColor(R.color.bg_white_2)) // Varsayılan arka plan rengi
        }

        // Tıklama olayını ayarla
        holder.cardView.setOnClickListener {
            // Eski seçimin rengini varsayılan renge döndür
            notifyItemChanged(selectedPosition)

            // Yeni seçimi belirle
            selectedPosition = holder.adapterPosition
            selectedCardType = type // Seçilen CardType değişkene atanır

            // Yeni seçimin rengini güncelle
            notifyItemChanged(selectedPosition)

            // İsteğe bağlı: Listener ile seçilen CardType'ı üst katmana ilet
            listener?.onItemClicked(selectedCardType)
        }



    }

}