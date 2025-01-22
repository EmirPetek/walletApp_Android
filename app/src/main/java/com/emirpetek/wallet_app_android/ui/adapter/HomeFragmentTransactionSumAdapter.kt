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
import com.emirpetek.wallet_app_android.data.model.enum.TransactionDirection
import com.emirpetek.wallet_app_android.data.model.enum.TransactionType
import com.emirpetek.wallet_app_android.ui.viewmodel.home.HomeViewModel

class HomeFragmentTransactionSumAdapter(
    private val mContext: Context,
    private val transactionList: List<Transaction>,
    private val viewModel: HomeViewModel,
    val viewLifecycleOwner: LifecycleOwner,
    val progressBarHomeFragmentTransactions: ProgressBar
): RecyclerView.Adapter<HomeFragmentTransactionSumAdapter.CardTransaction>() {


    inner class CardTransaction(view: View) : RecyclerView.ViewHolder(view){
        val textViewCardTransactionSumName: TextView = view.findViewById(R.id.textViewCardTransactionSumName)
        val textViewCardTransactionSumType: TextView = view.findViewById(R.id.textViewCardTransactionSumType)
        val textViewCardTransactionSumAmount: TextView = view.findViewById(R.id.textViewCardTransactionSumAmount)
        val imageViewCardTransactionTypeSum: ImageView = view.findViewById(R.id.imageViewCardTransactionTypeSum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTransaction {
        return CardTransaction(LayoutInflater.from(mContext).inflate(R.layout.cardview_transaction_sum,parent,false))
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    override fun onBindViewHolder(holder: CardTransaction, position: Int) {
        val transaction = transactionList[position]

        holder.textViewCardTransactionSumType.text = mContext.getString(transaction.transactionType.stringResId)

        if (transaction.transactionDirection.equals(TransactionDirection.NEGATIVE)){
            holder.textViewCardTransactionSumAmount.text = "- ${transaction.amount} ${transaction.currency}"
            holder.textViewCardTransactionSumAmount.setTextColor(Color.RED)
        }else{
            holder.textViewCardTransactionSumAmount.text = "+ ${transaction.amount} ${transaction.currency}"
            holder.textViewCardTransactionSumAmount.setTextColor(Color.GREEN)
        }

        when(transaction.transactionType){
            TransactionType.TRANSFER -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeSum,R.drawable.money_transfer)

                viewModel.getUserFullname(transaction.transferReceiverUserId!!)
                viewModel.fullname.observe(viewLifecycleOwner) { result ->
                    result.onSuccess { fullname ->
                        holder.textViewCardTransactionSumName.text = fullname }
                    result.onFailure { exception ->
                        Log.e("onFailure: ", "Hata: $exception") }
                }
            }

            TransactionType.DEPOSIT -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeSum,R.drawable.load_balance)
                holder.textViewCardTransactionSumName.text = "${mContext.getString(transaction.transactionType.stringResId)} ${transaction.amount} ${transaction.currency}"
            }
            TransactionType.WITHDRAWAL -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeSum,R.drawable.withdrawal)
                holder.textViewCardTransactionSumName.text = "${mContext.getString(transaction.transactionType.stringResId)} ${transaction.amount} ${transaction.currency}"
            }
            TransactionType.PAYMENT -> R.drawable.random_payment
            else -> {
                loadGlide(mContext,holder.imageViewCardTransactionTypeSum,R.drawable.money_transfer)
                holder.textViewCardTransactionSumName.text = "${mContext.getString(transaction.transactionType.stringResId)} ${transaction.amount} ${transaction.currency}"
            }

        }

//        Glide.with(mContext)
//            .load(
//                when(transaction.transactionType){
//                    TransactionType.TRANSFER -> R.drawable.money_transfer
//                    TransactionType.BILL_PAYMENT -> R.drawable.pay_bill
//                    TransactionType.DEPOSIT -> R.drawable.load_balance
//                    TransactionType.WITHDRAWAL -> R.drawable.withdrawal
//                    TransactionType.PAYMENT -> R.drawable.random_payment
//                    else -> R.drawable.money_transfer
//                }
//            )
//            .into(holder.imageViewCardTransactionTypeSum)





//
//        if (transaction.transactionType == TransactionType.TRANSFER) {
//                viewModel.getUserFullname(transaction.transferReceiverUserId!!)
//                viewModel.fullname.observe(viewLifecycleOwner) { result ->
//                    result.onSuccess { fullname ->
//                        holder.textViewCardTransactionSumName.text = fullname }
//                    result.onFailure { exception ->
//                        Log.e("onFailure: ", "Hata: $exception") }
//                }
//        }


        if (position == itemCount - 1 || transactionList.isEmpty()) progressBarHomeFragmentTransactions.visibility = View.GONE
    }

    fun loadGlide(mContext: Context, imageView: ImageView, file: Int){
        Glide.with(mContext).load(file).into(imageView)
    }


}