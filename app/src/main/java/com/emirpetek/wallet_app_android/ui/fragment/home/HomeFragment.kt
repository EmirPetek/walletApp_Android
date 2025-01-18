    package com.emirpetek.wallet_app_android.ui.fragment.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.databinding.FragmentHomeBinding
import com.emirpetek.wallet_app_android.ui.adapter.HomeFragmentCardAdapter
import com.emirpetek.wallet_app_android.ui.viewmodel.home.HomeViewModel

    class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter : HomeFragmentCardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        val userID = viewModel.getUserID(requireContext())
        binding.textViewFragmentHomeNoCardAlert.visibility = View.GONE
        binding.layoutAddCard.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_addCardFragment) }


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



        return binding.root
    }
}