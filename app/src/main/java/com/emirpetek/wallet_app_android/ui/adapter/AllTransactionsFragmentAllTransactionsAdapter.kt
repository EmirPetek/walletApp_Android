package com.emirpetek.wallet_app_android.ui.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.model.enum.PaymentCompany
import com.emirpetek.wallet_app_android.data.model.enum.TransactionDirection
import com.emirpetek.wallet_app_android.data.model.enum.TransactionType
import com.emirpetek.wallet_app_android.ui.viewmodel.allTransactions.AllTransactionsViewModel
import com.emirpetek.wallet_app_android.util.TimeDateConversion

class AllTransactionsFragmentAllTransactionsAdapter(
    val mContext: Context,
    val list: List<Transaction>,
    val viewModel: AllTransactionsViewModel,
    val viewLifecycleOwner: LifecycleOwner,
    val progressBarAllTransactionsTransactions: ProgressBar
): RecyclerView.Adapter<AllTransactionsFragmentAllTransactionsAdapter.CardHolder>() {

    inner class CardHolder(view: View) : RecyclerView.ViewHolder(view){
        val textViewCardTransactionAllName: TextView = view.findViewById(R.id.textViewCardTransactionAllName)
        val textViewCardTransactionAllType: TextView = view.findViewById(R.id.textViewCardTransactionAllType)
        val textViewCardTransactionAllAmount: TextView = view.findViewById(R.id.textViewCardTransactionAllAmount)
        val textViewCardTransactionAllDate: TextView = view.findViewById(R.id.textViewCardTransactionAllDate)
        val textViewCardTransactionAllDescription: TextView = view.findViewById(R.id.textViewCardTransactionAllDescription)
        val imageViewCardTransactionTypeAll: ImageView = view.findViewById(R.id.imageViewCardTransactionTypeAll)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(mContext).inflate(R.layout.cardview_transaction_all,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val transaction = list[position]


        when(transaction.transactionType){
            TransactionType.TRANSFER -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeAll,R.drawable.money_transfer)
                viewModel.getUserFullname(transaction.transferReceiverUserId!!)
                viewModel.fullname.observe(viewLifecycleOwner) { result ->
                    result.onSuccess { fullname ->
                        holder.textViewCardTransactionAllName.text = fullname
                        viewModel.fullname.removeObservers(viewLifecycleOwner)
                    }
                    result.onFailure { exception ->
                        Log.e("onFailure: ", "Hata: $exception") }
                }
            }

            TransactionType.DEPOSIT -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeAll,R.drawable.load_balance)
                holder.textViewCardTransactionAllName.text = "${mContext.getString(transaction.transactionType.stringResId)} ${transaction.amount} ${transaction.currency}"
            }
            TransactionType.WITHDRAWAL -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeAll,R.drawable.withdrawal)
                holder.textViewCardTransactionAllName.text = "${mContext.getString(transaction.transactionType.stringResId)} ${transaction.amount} ${transaction.currency}"
            }

            TransactionType.BILL_PAYMENT -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeAll,R.drawable.pay_bill)
                holder.textViewCardTransactionAllName.text = "${transaction.description} ${transaction.amount} ${transaction.currency}"
            }

            TransactionType.PAYMENT -> {
                val icon = PaymentCompany.fromDescription(transaction.description!!)?.iconResId ?: R.drawable.random_payment
                loadGlide(mContext,holder.imageViewCardTransactionTypeAll,icon)
                holder.textViewCardTransactionAllName.text = "${transaction.description} ${transaction.amount} ${transaction.currency}"
            }

            else -> holder.textViewCardTransactionAllName.text = "${mContext.getString(transaction.transactionType.stringResId)} ${transaction.amount} ${transaction.currency}"

        }




        holder.textViewCardTransactionAllType.text = mContext.getString(transaction.transactionType.stringResId)

        if (transaction.transactionDirection == TransactionDirection.NEGATIVE){
            holder.textViewCardTransactionAllAmount.text = "- ${transaction.amount} ${transaction.currency}"
            holder.textViewCardTransactionAllAmount.setTextColor(Color.RED)
        }else{
            holder.textViewCardTransactionAllAmount.text = "+ ${transaction.amount} ${transaction.currency}"
            holder.textViewCardTransactionAllAmount.setTextColor(Color.GREEN)
        }


        holder.textViewCardTransactionAllDescription.text = mContext.getString(R.string.description_label) + " " + transaction.description
        holder.textViewCardTransactionAllDate.text = TimeDateConversion().formatTimestamp(transaction.transactionDate)

        if (position == itemCount - 1 || list.isEmpty()) progressBarAllTransactionsTransactions.visibility = View.GONE

    }

    fun loadGlide(mContext: Context, imageView: ImageView, file: Int){
        Glide.with(mContext).load(file).into(imageView)
    }

}