package com.emirpetek.wallet_app_android.ui.fragment.allTransactions

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.databinding.FragmentAllTransactionsBinding
import com.emirpetek.wallet_app_android.ui.adapter.AllTransactionsFragmentAllTransactionsAdapter
import com.emirpetek.wallet_app_android.ui.adapter.AllTransactionsFragmentCardsAdapter
import com.emirpetek.wallet_app_android.ui.viewmodel.allTransactions.AllTransactionsViewModel
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility

class AllTransactionsFragment : Fragment() {



    private val viewModel: AllTransactionsViewModel by viewModels()
    private lateinit var binding: FragmentAllTransactionsBinding
    private lateinit var transactionAdapter: AllTransactionsFragmentAllTransactionsAdapter
    private lateinit var cardAdapter: AllTransactionsFragmentCardsAdapter
    private var selectedCardItem: CardDTO? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllTransactionsBinding.inflate(inflater,container,false)
        val userID = viewModel.getUserID(requireContext())

        ManageBottomBarVisibility(requireActivity()).hideBottomNav()
        binding.imageViewAllTransactionsBackButton.setOnClickListener { findNavController().popBackStack() }

        binding.progressBarAllTransactionsCards.visibility = View.VISIBLE

        getTransactions(userID)


        viewModel.getUserCards(GetCardRequest(userID))
        viewModel.cardsResult.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { list ->

                if (list.isEmpty()) binding.textViewAllTransactionsNoCardHere.visibility = View.VISIBLE

                binding.recyclerviewAllTransactionsCards.setHasFixedSize(true)
                binding.recyclerviewAllTransactionsCards.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,false)
                cardAdapter = AllTransactionsFragmentCardsAdapter(requireContext(),list,binding.progressBarAllTransactionsCards)
                binding.recyclerviewAllTransactionsCards.adapter = cardAdapter

                cardAdapter.setOnItemClickListener(object : AllTransactionsFragmentCardsAdapter.OnItemClickListener{
                    override fun onItemClicked(selectedCard: CardDTO) {
                        selectedCardItem = selectedCard
                        getTransactions(userID)
                    }
                })
            }
        })





        return binding.root
    }



    fun getTransactions(userID: Long){

        binding.progressBarAllTransactionsTransactions.visibility = View.VISIBLE

        viewModel.getTransactions(userID)
        viewModel.transactions.observe(viewLifecycleOwner, Observer { result ->

            result.onSuccess { list ->

                val filteredList = if (selectedCardItem != null) {
                    list.filter { transaction -> transaction.transactionCardId == selectedCardItem!!.id }
                } else {
                    list
                }

                if (filteredList.isEmpty()) {
                    binding.progressBarAllTransactionsTransactions.visibility = View.GONE
                    binding.textViewAllTransactionsNoTransactionsHere.visibility = View.VISIBLE
                }else{
                    binding.textViewAllTransactionsNoTransactionsHere.visibility = View.GONE
                }

                binding.recyclerViewAllTransactions.setHasFixedSize(true)
                binding.recyclerViewAllTransactions.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,false)
                transactionAdapter = AllTransactionsFragmentAllTransactionsAdapter(requireContext(),filteredList,viewModel,viewLifecycleOwner,binding.progressBarAllTransactionsTransactions)
                binding.recyclerViewAllTransactions.adapter = transactionAdapter
            }
        })
    }
}