package com.emirpetek.wallet_app_android.ui.fragment.login

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
import com.emirpetek.wallet_app_android.ui.viewmodel.login.ForgotPasswordEmailViewModel
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.databinding.FragmentForgotPasswordEmailBinding
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility

class ForgotPasswordEmailFragment : Fragment() {

    private val viewModel: ForgotPasswordEmailViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordEmailBinding.inflate(inflater,container,false)

        ManageBottomBarVisibility(requireActivity()).hideBottomNav()


        binding.buttonForgotPasswordChange.setOnClickListener {

            val email = binding.editTextForgotPasswordEmail.text.toString().trim()

            if (email.isEmpty()){
                Toast.makeText(requireContext(),getString(R.string.fill_all_place),Toast.LENGTH_SHORT).show()
            }else{
                viewModel.isEmailExist(email)
                viewModel.isEmailExist.observe(viewLifecycleOwner, Observer { result ->
                    result.onSuccess { response ->
                        if (response) {

                            viewModel.getUserIdFromEmail(email)
                            viewModel.userID.observe(viewLifecycleOwner, Observer { result ->
                                result.onSuccess { userID ->
                                    Log.e("userID", userID.toString())
                                    val bundle: Bundle = Bundle().apply { putLong("userID",userID); putString("email",email) }
                                    findNavController().navigate(R.id.action_forgotPasswordEmailFragment_to_forgotPasswordVerifyCodeFragment,bundle)
                                }
                                result.onFailure {
                                    Log.e("onfailure", it.toString())
                                    Toast.makeText(requireContext(),getString(R.string.error),Toast.LENGTH_SHORT).show()
                                }
                            })

                        }else{
                            Log.e("email: ", email)
                            Toast.makeText(requireContext(),getString(R.string.user_not_found),Toast.LENGTH_SHORT).show()
                        }
                    }

                    result.onFailure {
                        Log.e("onFailure: ", it.toString())
                        Toast.makeText(requireContext(),"Error: "+ getString(R.string.user_not_found),Toast.LENGTH_SHORT).show()
                    }


                })

                viewModel.isEmailExist.removeObservers(viewLifecycleOwner)
            }


        }


        return binding.root
    }
}