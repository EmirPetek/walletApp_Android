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
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.enum.PasswordResponse
import com.emirpetek.wallet_app_android.data.request.PasswordChangeRequest
import com.emirpetek.wallet_app_android.databinding.FragmentForgotPasswordChangePasswordBinding
import com.emirpetek.wallet_app_android.ui.viewmodel.login.ForgotPasswordChangePasswordViewModel
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility

class ForgotPasswordChangePasswordFragment : Fragment() {


    private val viewModel: ForgotPasswordChangePasswordViewModel by viewModels()
    private lateinit var binding: FragmentForgotPasswordChangePasswordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordChangePasswordBinding.inflate(inflater,container,false)

        ManageBottomBarVisibility(requireActivity()).hideBottomNav()


        val id = arguments?.getLong("userID")!!

        binding.buttonForgotPasswordChangePassword.setOnClickListener {

            val newPassword = binding.editTextForgotPasswordChangePassword.text.toString().trim()
            val newPasswordConfirm = binding.editTextForgotPasswordChangePasswordConfirm.text.toString().trim()

            if (newPassword != newPasswordConfirm){
                Toast.makeText(requireContext(),getString(R.string.password_doesnt_match),Toast.LENGTH_SHORT).show()
            }else{

                val passwordChangeRequest = PasswordChangeRequest(
                    id,
                    null,
                    newPasswordConfirm
                )

                viewModel.changePassword(passwordChangeRequest)
                viewModel.passwordChangeResult.observe(viewLifecycleOwner, Observer { result ->

                    Log.e("userID", id.toString())

                    result.onSuccess { response ->
                        if (response == PasswordResponse.SUCCESSFUL){
                            Toast.makeText(requireContext(),getString(R.string.password_change_success),Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_forgotPasswordChangePasswordFragment_to_loginFragment)
                        }else if (response == PasswordResponse.OLD_PASSWORD_INCORRECT) {
                            Toast.makeText(requireContext(),getString(R.string.old_password_incorrect),Toast.LENGTH_SHORT).show()
                        }else if (response == PasswordResponse.USER_NOT_FOUND){
                            Toast.makeText(requireContext(),getString(R.string.user_not_found),Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(),"Error : " + response.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }

                    result.onFailure { response ->
                        Toast.makeText(requireContext(),"Error: " + response.toString(),Toast.LENGTH_SHORT).show()
                    }


                })

                viewModel.passwordChangeResult.removeObservers(viewLifecycleOwner)
            }


        }

        return binding.root
    }
}