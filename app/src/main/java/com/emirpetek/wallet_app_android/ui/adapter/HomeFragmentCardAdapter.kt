package com.emirpetek.wallet_app_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.model.enum.CardType

class HomeFragmentCardAdapter(
    val mContext: Context,
    val cardList: List<CardDTO>,
    val progressBarHomeFragmentCards: ProgressBar
): RecyclerView.Adapter<HomeFragmentCardAdapter.CardHolder>() {

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view){
        val cardviewCardComponent: CardView = view.findViewById(R.id.cardviewCardComponent)
        val textViewCardviewBalance : TextView = view.findViewById(R.id.textViewCardviewBalance)
        val imageViewCardviewCardType: ImageView = view.findViewById(R.id.imageViewCardviewCardType)
        val textViewCardviewCardNumber: TextView = view.findViewById(R.id.textViewCardviewCardNumber)
        val textViewCardviewCardHolder: TextView = view.findViewById(R.id.textViewCardviewCardHolder)
        val textViewCardviewExpireDate: TextView = view.findViewById(R.id.textViewCardviewExpireDate)
        val textViewCardviewCvvNumber: TextView = view.findViewById(R.id.textViewCardviewCvvNumber)
        val textViewCardIbanNumber: TextView = view.findViewById(R.id.textViewCardIbanNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.cardview_card,parent,false)
        return CardHolder(view)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val cardInfo = cardList[position]

        val cardNumberFirst4 = cardInfo.cardNumber.substring(0,4)
        val cardNumberFourth4 = cardInfo.cardNumber.substring(12,16)

        val cardNumber = "$cardNumberFirst4 **** **** $cardNumberFourth4"
        val balanceAndCurrency = "${cardInfo.balance} ${cardInfo.currency}"

        var cardTypeLogoImg: Int = 0
        var cardTypeGradient: Int = 0
        when(cardInfo.cardType){
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



        Glide.with(mContext).load(cardTypeLogoImg).into(holder.imageViewCardviewCardType)
        holder.cardviewCardComponent.setBackgroundResource(cardTypeGradient)



        holder.textViewCardviewCardHolder.setText(cardInfo.cardHolder)
        holder.textViewCardviewCardNumber.setText(cardNumber)
        holder.textViewCardviewExpireDate.setText(cardInfo.expireDate)
        holder.textViewCardviewCvvNumber.setText(cardInfo.cvv.toString())
        holder.textViewCardviewBalance.setText(balanceAndCurrency)
        holder.textViewCardIbanNumber.setText(mContext.getString(R.string.iban) + ": " + cardInfo.accountID)


        if (position == itemCount - 1 || cardList.isEmpty()) progressBarHomeFragmentCards.visibility = View.GONE

    }


}