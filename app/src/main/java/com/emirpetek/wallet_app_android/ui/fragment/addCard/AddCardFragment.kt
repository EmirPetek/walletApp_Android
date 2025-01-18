package com.emirpetek.wallet_app_android.ui.fragment.addCard

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.enum.CardType
import com.emirpetek.wallet_app_android.data.model.enum.CurrencyType
import com.emirpetek.wallet_app_android.data.request.CreateCardRequest
import com.emirpetek.wallet_app_android.databinding.FragmentAddCardBinding
import com.emirpetek.wallet_app_android.ui.adapter.AddCardFragmentCardCurrencyAdapter
import com.emirpetek.wallet_app_android.ui.adapter.AddCardFragmentCardTypeAdapter
import com.emirpetek.wallet_app_android.ui.viewmodel.addCard.AddCardViewModel
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal

class AddCardFragment : Fragment() {


    private val viewModel: AddCardViewModel by viewModels()
    private lateinit var binding: FragmentAddCardBinding
    private lateinit var cardTypeAdapter: AddCardFragmentCardTypeAdapter
    private lateinit var cardCurrencyAdapter: AddCardFragmentCardCurrencyAdapter
    private lateinit var selectedCardType : CardType
    private lateinit var selectedCurrency : CurrencyType
    private var isCardTypeSelected = false
    private var isCurrencySelected = false
    private var balance = BigDecimal.ZERO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddCardBinding.inflate(inflater,container,false)

        binding.imageViewAddCardBackButton.setOnClickListener { findNavController().popBackStack() }

        val userID = viewModel.getUserID(requireContext())

        ManageBottomBarVisibility(requireActivity()).hideBottomNav()

        val cardTypeList: List<CardType> = listOf(CardType.VISA,CardType.MASTERCARD,CardType.MAESTRO,CardType.UNIONPAY,CardType.DINERS_CLUB,CardType.AMERICAN_EXPRESS)
        binding.recyclerviewAddCardFragmentCardType.setHasFixedSize(true)
        binding.recyclerviewAddCardFragmentCardType.layoutManager = GridLayoutManager(requireContext(),3)
        cardTypeAdapter = AddCardFragmentCardTypeAdapter(requireContext(),cardTypeList)
        binding.recyclerviewAddCardFragmentCardType.adapter = cardTypeAdapter

        val cardCurrencyList: List<CurrencyType> = listOf(CurrencyType.TRY,CurrencyType.EUR,CurrencyType.USD)
        binding.recyclerviewAddCardFragmentCardCurrency.setHasFixedSize(true)
        binding.recyclerviewAddCardFragmentCardCurrency.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        cardCurrencyAdapter = AddCardFragmentCardCurrencyAdapter(requireContext(),cardCurrencyList)
        binding.recyclerviewAddCardFragmentCardCurrency.adapter = cardCurrencyAdapter


        cardTypeAdapter.setOnItemClickListener(object : AddCardFragmentCardTypeAdapter.OnItemClickListener{
            override fun onItemClicked(selectedItemCardType: CardType) {
                selectedCardType = selectedItemCardType
                isCardTypeSelected = true
            }
        })

        cardCurrencyAdapter.setOnItemClickListener(object : AddCardFragmentCardCurrencyAdapter.OnItemClickListener{
            override fun onItemClicked(selectedItemCurrency: CurrencyType) {
                selectedCurrency = selectedItemCurrency
                isCurrencySelected = true
            }
        })

        binding.buttonCreateCard.setOnClickListener {
            val cardHolder = binding.editTextAddCardCardHolder.text.toString().trim()
            val balanceText = binding.editTextAddCardBalance.text.trim().toString()

            val balance = if (balanceText.isEmpty()) BigDecimal.ZERO else BigDecimal(balanceText.toDouble())

            val errors = mutableListOf<String>()

            if (cardHolder.isEmpty() || !isCardTypeSelected || !isCurrencySelected) {
                errors.add(getString(R.string.fill_all_place))
            }
            if (balance <= BigDecimal.ZERO) {
                errors.add(getString(R.string.balance_not_zero_or_lower))
            }

            if (errors.isNotEmpty()) {
                Toast.makeText(requireContext(), errors.joinToString("\n"), Toast.LENGTH_LONG).show()
            } else {
                val createCardRequest = CreateCardRequest(
                    selectedCardType,
                    cardHolder,
                    userID,
                    balance,
                    selectedCurrency
                )

                runBlocking {
                    viewModel.createCard(createCardRequest)
                    viewModel.createCardResult.observe(viewLifecycleOwner, Observer { result ->
                        result.onSuccess { it ->
                            if (it) {
                                Toast.makeText(requireContext(),getString(R.string.card_created_successfully),Toast.LENGTH_LONG).show()
                                findNavController().popBackStack()
                            }
                            else Toast.makeText(requireContext(),getString(R.string.card_created_failure),Toast.LENGTH_LONG).show()
                        }
                        result.onFailure { it->
                            Log.e("it exception: ", it.toString())
                            Toast.makeText(requireContext(),getString(R.string.card_created_failure),Toast.LENGTH_LONG).show()
                        }
                    })
                }



            }
        }




        return binding.root
    }
}