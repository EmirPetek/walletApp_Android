package com.emirpetek.wallet_app_android.ui.fragment.profile

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.databinding.FragmentProfileBinding
import com.emirpetek.wallet_app_android.ui.viewmodel.profile.ProfileViewModel
import com.emirpetek.wallet_app_android.util.TimeDateConversion

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    private var userID : Long = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        userID = viewModel.getUserID(requireContext())


        viewModel.getUserInfo(userID)
        viewModel.user.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess { user ->

                val fullname = "${user.firstName}  ${user.lastName}"
                binding.textViewProfileFragmentFullname.text = fullname

                binding.textViewProfileFragmentUserID.text = user.id.toString()
                binding.textViewProfileFragmentBirthdate.text = user.birthdate
                binding.textViewProfileFragmentEmail.text = user.email
                binding.textViewProfileFragmentCreatedAt.text = TimeDateConversion().formatTimestamp(user.createdAt)
            }
        })

        viewModel.getNumOfCards(userID)
        viewModel.getNumOfTransactions(userID)


        viewModel.numOfCards.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                binding.textViewProfileFragmentNumOfCards.text = "$it ${getString(R.string.cards)}"
            } })


        viewModel.numOfTransactions.observe(viewLifecycleOwner, Observer { result ->
            result.onSuccess {
                binding.textViewProfileFragmentNumOfTransactions.text = "$it ${getString(R.string.transactions)}"
            } })



        return binding.root
    }
}