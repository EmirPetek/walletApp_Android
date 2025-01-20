package com.emirpetek.wallet_app_android.ui.fragment.moneyTransfer

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.dto.CardDTO
import com.emirpetek.wallet_app_android.data.model.enum.MoneyTransferReturnStatements
import com.emirpetek.wallet_app_android.data.request.GetCardRequest
import com.emirpetek.wallet_app_android.data.request.MoneyTransferRequest
import com.emirpetek.wallet_app_android.databinding.FragmentMoneyTransferBinding
import com.emirpetek.wallet_app_android.ui.adapter.HomeFragmentCardAdapter
import com.emirpetek.wallet_app_android.ui.adapter.MoneyTransferMyCardAdapter
import com.emirpetek.wallet_app_android.ui.fragment.home.HomeFragment
import com.emirpetek.wallet_app_android.ui.viewmodel.moneyTransfer.MoneyTransferViewModel
import java.math.BigDecimal

class MoneyTransferFragment : Fragment() {



    private val viewModel: MoneyTransferViewModel by viewModels()
    private lateinit var binding: FragmentMoneyTransferBinding
    private lateinit var cardAdapter: MoneyTransferMyCardAdapter
    private var selectedCardItem: CardDTO? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoneyTransferBinding.inflate(inflater,container,false)

        val userID = viewModel.getUserID(requireContext())

        binding.textViewMoneyTransferNoCardAlert.visibility = View.GONE

        viewModel.getUserCards(GetCardRequest(userID))
        viewModel.cardsResult.observe(viewLifecycleOwner, Observer { result ->

            result.onSuccess { list ->

                if (list.size == 0) binding.textViewMoneyTransferNoCardAlert.visibility = View.VISIBLE
                else binding.textViewMoneyTransferNoCardAlert.visibility = View.GONE

                binding.recyclerviewMoneyTransferMyCards.setHasFixedSize(true)
                binding.recyclerviewMoneyTransferMyCards.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.HORIZONTAL,false)
                cardAdapter = MoneyTransferMyCardAdapter(requireContext(),list)
                binding.recyclerviewMoneyTransferMyCards.adapter = cardAdapter

                cardAdapter.setOnItemClickListener(object : MoneyTransferMyCardAdapter.OnItemClickListener{
                    override fun onItemClicked(selectedCard: CardDTO) {
                        selectedCardItem = selectedCard
                    }
                })
            }
        })



        binding.buttonSendMoney.setOnClickListener {
            if (!isCardSelected()) return@setOnClickListener

            val receiverIbanNumber = binding.editTextTransferMoneyIbanNumber.text.toString().trim()
            val amount = parseAmount(binding.editTextMoneyTransferAmount.text.toString())
            val description = binding.editTextMoneyTransferDescription.text.toString().trim()

            if (!isAmountValid(amount)) return@setOnClickListener

            initiateMoneyTransfer(userID,receiverIbanNumber, amount, description)
        }







        return binding.root
    }

    public fun showToast(message:String){ Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()}

    private fun isCardSelected(): Boolean {
        return if (selectedCardItem == null) {
            showToast(getString(R.string.select_card_alert))
            false
        } else {
            true
        }
    }

    private fun parseAmount(amountText: String): BigDecimal {
        return if (amountText.isBlank()) BigDecimal.ZERO else BigDecimal(amountText.trim())
    }

    private fun isAmountValid(amount: BigDecimal): Boolean {
        return if (amount <= BigDecimal.ZERO || amount > (selectedCardItem?.balance)) {
            showToast(getString(R.string.amount_cannot_be_lower_balance))
            false
        } else {
            true
        }
    }

    private fun initiateMoneyTransfer(userID: Long, receiverIbanNumber: String, amount: BigDecimal, description: String) {
        viewModel.transferMoney(
            MoneyTransferRequest(
                userID,
                selectedCardItem!!.accountID,
                receiverIbanNumber,
                amount,
                selectedCardItem!!.currency,
                description
            )
        )

        observeTransferResult()
    }

    private fun observeTransferResult() {
        viewModel.transferResult.observe(viewLifecycleOwner) { response ->
            response.onSuccess { handleTransferSuccess(it) }
            response.onFailure { showToast(getString(R.string.transfer_failure_onfailure_state)) }
            viewModel.transferResult.removeObservers(viewLifecycleOwner)
        }
    }

    private fun handleTransferSuccess(result: MoneyTransferReturnStatements) {

        if (result == MoneyTransferReturnStatements.SUCCESSFUL_TRANSFER) showCustomAlertDialog()
        else{
            val message = when (result) {
                MoneyTransferReturnStatements.FAILURE_AMOUNT_LOWER_THAN_ZERO -> getString(R.string.transfer_failure_amount_lower_than_zero)
                MoneyTransferReturnStatements.FAILURE_DIFFERENT_CURRENCY -> getString(R.string.transfer_failure_different_currency)
                MoneyTransferReturnStatements.FAILURE_INVALID_IBAN_NUMBER -> getString(R.string.transfer_failure_invalid_iban_number)
                MoneyTransferReturnStatements.FAILURE_SERVER -> getString(R.string.transfer_failure_failure_server)
                else -> getString(R.string.transfer_failure_failure_server)
            }
            showToast(message)
        }
    }

    fun showCustomAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_transfer_successful, null)
        dialogBuilder.setView(dialogView)

        val dialog = dialogBuilder.create()

        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()


        dialogView.findViewById<Button>(R.id.buttonTransferSuccessfulOk)?.apply {
            setOnClickListener {
                dialog.dismiss()
                val fragmentId = findNavController().currentDestination?.id
                findNavController().popBackStack(fragmentId!!,true)
                findNavController().navigate(fragmentId)
            }
        }

    }



}