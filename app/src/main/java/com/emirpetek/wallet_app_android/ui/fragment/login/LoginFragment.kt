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
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.databinding.FragmentLoginBinding
import com.emirpetek.wallet_app_android.ui.viewmodel.login.LoginViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class LoginFragment : Fragment() {



    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)


        binding.textViewLoginCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.editTextLoginEmail.setText("emirpetek2002@gmail.com")
        binding.editTextLoginPassword.setText("emir1234")

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextLoginEmail.text.toString()
            val password = binding.editTextLoginPassword.text.toString()

            viewModel.login(email,password)
            viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
                result.onSuccess { userDto ->
                    viewModel.saveUserIdToSP(userDto.id,requireContext())
                   findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

                }

                result.onFailure { it ->
                    viewModel.loginResult.removeObservers(viewLifecycleOwner)
                    Log.e("result error:  ", result.toString())
                    Toast.makeText(requireContext(),getString(R.string.login_error),Toast.LENGTH_SHORT).show()
                }

            })
        }


        return binding.root
    }
}