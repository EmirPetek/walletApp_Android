    package com.emirpetek.wallet_app_android.ui.fragment.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.Transaction
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.databinding.FragmentHomeBinding
import com.emirpetek.wallet_app_android.ui.adapter.HomeFragmentCardAdapter
import com.emirpetek.wallet_app_android.ui.adapter.HomeFragmentTransactionSumAdapter
import com.emirpetek.wallet_app_android.ui.viewmodel.home.HomeViewModel
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility

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

        ManageBottomBarVisibility(requireActivity()).showBottomNav()

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
                adapter = HomeFragmentCardAdapter(requireContext(),list)
                binding.recyclerviewHomeCards.adapter = adapter
            }
        })

        viewModel.getTransactions(userID)
        viewModel.transactions.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { list ->

                if (list.isEmpty()) binding.textViewHomeFragmentNoTransactionAlert.visibility = View.VISIBLE
                else binding.textViewHomeFragmentNoTransactionAlert.visibility = View.GONE

                var newList : List<Transaction>
                val minSize = minOf(10,list.size)
                if (list.isNotEmpty()) newList = list.subList(0,minSize)
                else newList = list

                binding.recyclerviewHomeTransactionSum.setHasFixedSize(true)
                binding.recyclerviewHomeTransactionSum.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                transactionAdapter = HomeFragmentTransactionSumAdapter(requireContext(),newList,viewModel,viewLifecycleOwner)
                binding.recyclerviewHomeTransactionSum.adapter = transactionAdapter

            }
        })





        return binding.root
    }
}