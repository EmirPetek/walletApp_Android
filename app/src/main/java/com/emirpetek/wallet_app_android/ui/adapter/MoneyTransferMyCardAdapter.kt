package com.emirpetek.wallet_app_android.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.model.enum.CardType
import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType

class MoneyTransferMyCardAdapter
    (val mContext: Context,
    val cardList: List<CardDTO>
): RecyclerView.Adapter<MoneyTransferMyCardAdapter.CardHolder>() {

    lateinit var selectedCard: CardDTO
    var selectedPosition = -1

    interface OnItemClickListener {
        fun onItemClicked(selectedCard: CardDTO)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class CardHolder(view: View): RecyclerView.ViewHolder(view){
        val cardNumber: TextView = view.findViewById(R.id.textViewMoneyTransferMyCardNumber)
        val cardBalanceCurrency: TextView = view.findViewById(R.id.textViewMoneyTransferCardBalanceCurrency)
        val layout: LinearLayout = view.findViewById(R.id.layoutMoneyTransferCardBackground)
        val cardViewBig: CardView = view.findViewById(R.id.cardviewMoneyTransferCard)
        val cardLogo: ImageView = view.findViewById(R.id.imageViewMoneyTransferCardType)

        val layout_inside1: LinearLayout = view.findViewById(R.id.layout_inside1)
        val layout_inside2: LinearLayout = view.findViewById(R.id.layout_inside2)
        val layout_inside3: LinearLayout = view.findViewById(R.id.layout_inside3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_money_transfer_select_card,parent,false))
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val card = cardList[position]

        val cardNumberFirst4 = card.cardNumber.substring(0,4)
        val cardNumberFourth4 = card.cardNumber.substring(12,16)

        val cardNumber = "$cardNumberFirst4 **** **** $cardNumberFourth4"
        val balanceAndCurrency = "${card.balance} ${card.currency}"

        var cardTypeLogoImg: Int = 0
        var cardTypeGradient: Int = 0
        when(card.cardType){
            CardType.VISA -> {
                cardTypeLogoImg = R.drawable.visa_logo
                cardTypeGradient = R.drawable.visa_gradient
            }
            CardType.MASTERCARD -> {
                cardTypeLogoImg = R.drawable.mastercard_logo
                cardTypeGradient = R.drawable.maestro_card_gradient
            }
            CardType.MAESTRO -> {
                cardTypeLogoImg = R.drawable.maestro_logo
                cardTypeGradient = R.drawable.maestro_card_gradient
            }
            CardType.AMERICAN_EXPRESS -> {
                cardTypeLogoImg = R.drawable.american_express_logo
                cardTypeGradient = R.drawable.american_express_gradient
            }
            CardType.UNIONPAY -> {
                cardTypeLogoImg = R.drawable.union_pay_logo
                cardTypeGradient = R.drawable.diners_club_gradient
            }
            CardType.DINERS_CLUB -> {
                cardTypeLogoImg = R.drawable.diners_club_logo
                cardTypeGradient = R.drawable.diners_club_gradient
            }
            else -> {
                cardTypeLogoImg = R.drawable.visa_logo
                cardTypeGradient = R.drawable.visa_gradient
            }
        }

        Glide.with(mContext).load(cardTypeLogoImg).into(holder.cardLogo)
        holder.layout.setBackgroundResource(cardTypeGradient)

        //holder.layout_inside1.setBackgroundColor(ColorDrawable(Color.TRANSPARENT).color)



        holder.cardNumber.setText(cardNumber)
        holder.cardBalanceCurrency.setText(balanceAndCurrency)

            // Eğer bu pozisyon seçili ise arka plan rengini değiştir
            if (selectedPosition == position) {
                holder.layout_inside1.setBackgroundColor(mContext.getColor(R.color.selected_card)) // Seçili arka plan rengi
                holder.cardBalanceCurrency.setTextColor(mContext.getColor(R.color.bg_white_2))
            } else {
                holder.layout_inside1.setBackgroundColor(mContext.getColor(R.color.bg_white_2)) // Varsayılan arka plan rengi
                holder.cardBalanceCurrency.setTextColor(mContext.getColor(R.color.selected_card))
            }

                    // Tıklama olayını ayarla
                holder.cardViewBig.setOnClickListener {
                // Eski seçimin rengini varsayılan renge döndür
                notifyItemChanged(selectedPosition)

                // Yeni seçimi belirle
                selectedPosition = holder.adapterPosition
                selectedCard = card // Seçilen CardType değişkene atanır

                // Yeni seçimin rengini güncelle
                notifyItemChanged(selectedPosition)

                // İsteğe bağlı: Listener ile seçilen CardType'ı üst katmana ilet
                listener?.onItemClicked(selectedCard)
            }



    }
}