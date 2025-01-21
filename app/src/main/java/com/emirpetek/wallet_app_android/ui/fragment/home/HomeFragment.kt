    package com.emirpetek.wallet_app_android.ui.fragment.home

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.LoadBalanceRequest
import com.emirpetek.wallet_app_android.databinding.FragmentHomeBinding
import com.emirpetek.wallet_app_android.ui.adapter.HomeFragmentCardAdapter
import com.emirpetek.wallet_app_android.ui.adapter.HomeFragmentTransactionSumAdapter
import com.emirpetek.wallet_app_android.ui.viewmodel.home.HomeViewModel
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.math.BigDecimal

    class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : HomeFragmentCardAdapter
    private lateinit var transactionAdapter : HomeFragmentTransactionSumAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        val userID = viewModel.getUserID(requireContext())
        binding.textViewFragmentHomeNoCardAlert.visibility = View.GONE
        binding.textViewHomeFragmentNoTransactionAlert.visibility = View.GONE
        binding.layoutAddCard.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_addCardFragment) }
        binding.textViewHomeFragmentViewAllTransactions.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_allTransactionsFragment) }

        ManageBottomBarVisibility(requireActivity()).showBottomNav()

        binding.progressBarHomeFragmentCards.visibility = View.VISIBLE
        viewModel.getUserCards(GetCardRequest(userID))
        viewModel.cardsResult.observe(viewLifecycleOwner, Observer { result ->

            result.onSuccess { list ->

                if (list.size == 0) binding.textViewFragmentHomeNoCardAlert.visibility = View.VISIBLE
                else binding.textViewFragmentHomeNoCardAlert.visibility = View.GONE
                var sizeText = ""
                if (list.size <=1) sizeText = "Card"
                else sizeText = "Cards"
                binding.textViewHomeMyCardsTitle.setText(getString(R.string.my_cards) + " (${list.size} $sizeText)")


                binding.recyclerviewHomeCards.setHasFixedSize(true)
                binding.recyclerviewHomeCards.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapter = HomeFragmentCardAdapter(requireContext(),list,binding.progressBarHomeFragmentCards)
                binding.recyclerviewHomeCards.adapter = adapter

                val cardNumberList: ArrayList<Pair<String,Long>> = arrayListOf()
                for (item in list){
                    val cardNumberFirst4 = item.cardNumber.substring(0,4)
                    val cardNumberFourth4 = item.cardNumber.substring(12,16)

                    val cardNumber = "$cardNumberFirst4 **** **** $cardNumberFourth4"
                    val id = item.id
                    val itemAdded = Pair(cardNumber,id)
                    Log.e("itemAdded: ", itemAdded.toString())
                    cardNumberList.add(itemAdded)
                }
                Log.e("cardNumbersBefore: ", cardNumberList.toString())
                binding.cardLoadBalance.setOnClickListener {
                    Log.e("cardNumbersBefore2: ", cardNumberList.toString())
                    showCustomBottomSheetDialog(requireContext(),cardNumberList,userID)
                }

            }
        })

        binding.progressBarHomeFragmentTransactions.visibility = View.VISIBLE
        viewModel.getTransactions(userID)
        viewModel.transactions.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { list ->

                if (list.isEmpty()) {
                    binding.textViewHomeFragmentNoTransactionAlert.visibility = View.VISIBLE
                    binding.progressBarHomeFragmentTransactions.visibility = View.GONE
                }
                else binding.textViewHomeFragmentNoTransactionAlert.visibility = View.GONE

                var newList : List<Transaction>
                val minSize = minOf(10,list.size)
                if (list.isNotEmpty()) newList = list.subList(0,minSize)
                else newList = list


                binding.recyclerviewHomeTransactionSum.setHasFixedSize(true)
                binding.recyclerviewHomeTransactionSum.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                transactionAdapter = HomeFragmentTransactionSumAdapter(requireContext(),newList,viewModel,viewLifecycleOwner,binding.progressBarHomeFragmentTransactions)
                binding.recyclerviewHomeTransactionSum.adapter = transactionAdapter

            }
        })





        return binding.root
    }

        fun showCustomBottomSheetDialog(mContext: Context, cardNumbers: ArrayList<Pair<String,Long>>,userID:Long) {

            Log.e("cardnumbers", cardNumbers.toString())

            // BottomSheetDialog oluştur
            val bottomSheetDialog = BottomSheetDialog(mContext)
            val view = LayoutInflater.from(mContext).inflate(R.layout.bottom_sheet_dialog_load_balance, null)
            bottomSheetDialog.setContentView(view)

            val closeButton = view.findViewById<ImageView>(R.id.imageViewBSDLoadBalanceCloseDialog)
            val editTextAmount = view.findViewById<EditText>(R.id.editTextBSDAmount)
            val spinner = view.findViewById<Spinner>(R.id.spinnerBSDLoadBalanceCards)
            val submitButton = view.findViewById<Button>(R.id.buttonBSDLoadBalance)

            // Spinner'ı kart numaralarıyla doldur
            val adapter = ArrayAdapter(mContext, android.R.layout.simple_spinner_item, cardNumbers.map { it.first })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            // Kapatma butonuna tıklama
            closeButton.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            // Load butonuna tıklama
            submitButton.setOnClickListener {
                val selectedCard = spinner.selectedItem?.toString() ?: ""


                val cardID = cardNumbers.find { it.first == selectedCard }?.second!!


                val balanceText = editTextAmount.text.trim().toString()


                if (selectedCard.isNotEmpty() && balanceText.isNotEmpty()) {
                    Log.e("bsd tıklanması: ", "$selectedCard ve ${BigDecimal(balanceText.toDouble())} cardID: $cardID")
                    val balance = BigDecimal(balanceText.toDouble())
                    val loadBalanceRequest = LoadBalanceRequest(
                        userID,
                        balance,
                        cardID)

                    viewModel.loadBalance(loadBalanceRequest)
                    viewModel.loadBalanceResult.observe(viewLifecycleOwner, Observer { result ->
                        result.onSuccess { response ->
                            if (response) Toast.makeText(requireContext(),getString(R.string.load_balance_process_success),Toast.LENGTH_SHORT).show()
                            else Toast.makeText(requireContext(),getString(R.string.load_balance_process_failure),Toast.LENGTH_SHORT).show()
                        }

                        result.onFailure{ Toast.makeText(requireContext(),getString(R.string.load_balance_process_failure),Toast.LENGTH_SHORT).show() }

                        bottomSheetDialog.dismiss()

                    })

                } else {
                    Toast.makeText(mContext, getString(R.string.fill_all_place), Toast.LENGTH_SHORT).show()
                }

            }

            // Dialogu göster
            bottomSheetDialog.show()
        }

    }