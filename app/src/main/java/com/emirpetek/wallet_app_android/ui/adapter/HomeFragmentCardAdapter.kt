package com.emirpetek.wallet_app_android.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.dto.CardDTO

class HomeFragmentCardAdapter(
    val mContext:Context,
    val cardList: List<CardDTO>
): RecyclerView.Adapter<HomeFragmentCardAdapter.CardHolder>() {

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view){
        val cardviewCardComponent: CardView = view.findViewById(R.id.cardviewCardComponent)
        val textViewCardviewBalance : TextView = view.findViewById(R.id.textViewCardviewBalance)
        val imageViewCardviewCardType: ImageView = view.findViewById(R.id.imageViewCardviewCardType)
        val textViewCardviewCardNumber: TextView = view.findViewById(R.id.textViewCardviewCardNumber)
        val textViewCardviewCardHolder: TextView = view.findViewById(R.id.textViewCardviewCardHolder)
        val textViewCardviewExpireDate: TextView = view.findViewById(R.id.textViewCardviewExpireDate)
        val textViewCardviewCvvNumber: TextView = view.findViewById(R.id.textViewCardviewCvvNumber)
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
        //val cardNumberSecond4 = cardInfo.cardNumber.substring(4,7)
        //val cardNumberThird4 = cardInfo.cardNumber.substring(8,11)
        val cardNumberFourth4 = cardInfo.cardNumber.substring(12,16)

        val cardNumber = "$cardNumberFirst4 **** **** $cardNumberFourth4"
        val balanceAndCurrency = "${cardInfo.balance} ${cardInfo.currency}"

        holder.textViewCardviewCardHolder.setText(cardInfo.cardHolder)
        holder.textViewCardviewCardNumber.setText(cardNumber)
        holder.textViewCardviewExpireDate.setText(cardInfo.expireDate)
        holder.textViewCardviewCvvNumber.setText(cardInfo.cvv.toString())
        holder.textViewCardviewBalance.setText(balanceAndCurrency)



    }


}