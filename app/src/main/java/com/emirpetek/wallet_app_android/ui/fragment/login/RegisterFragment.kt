package com.emirpetek.wallet_app_android.ui.fragment.login

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.ZygotePreload
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.User
import com.emirpetek.wallet_app_android.data.request.RegisterRequest
import com.emirpetek.wallet_app_android.databinding.FragmentRegisterBinding
import com.emirpetek.wallet_app_android.ui.viewmodel.login.RegisterViewModel
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)

        ManageBottomBarVisibility(requireActivity()).hideBottomNav()

        mockData()
        binding.editTextRegisterBirthdate.setOnClickListener { showDatePickerDialog() }
        binding.buttonBackToLogin.setOnClickListener { findNavController().popBackStack() }
        binding.buttonRegister.setOnClickListener {

            if (
                binding.editTextRegisterFirstName.text.toString().isEmpty() ||
                binding.editTextRegisterLastName.text.toString().isEmpty() ||
                binding.editTextRegisterEmail.text.toString().isEmpty() ||
                binding.editTextRegisterPassword.text.toString().isEmpty() ||
                binding.editTextRegisterBirthdate.text.toString().isEmpty()){

                Toast.makeText(requireContext()
                    ,getString(R.string.fill_all_place)
                    ,Toast.LENGTH_SHORT).show()
            }else {

                val firstname = binding.editTextRegisterFirstName.text.toString()
                val lastname = binding.editTextRegisterLastName.text.toString()
                val email = binding.editTextRegisterEmail.text.toString()
                val password = binding.editTextRegisterPassword.text.toString()
                val birthdate = binding.editTextRegisterBirthdate.text.toString()

                val registerRequest = RegisterRequest(
                    User(
                        null,
                        firstname,
                        lastname,
                        email,
                        password,
                        birthdate,
                        System.currentTimeMillis()
                    )
                )


                runBlocking {

                    launch {
                        viewModel.registerUser(registerRequest)
                    }.join()

                    viewModel.registerResponse.observe(viewLifecycleOwner, Observer { result ->
                        //Log.e("result: ", result.toString())

                        result?.onSuccess { isSuccessful ->
                           // Log.e("fragment isSuccessful", "$isSuccessful ve email: $email")
                            if (isSuccessful) {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.register_successful),
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().popBackStack()
                            } else {
                                viewModel.registerResponse.removeObservers(viewLifecycleOwner)
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.register_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        result?.onFailure {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.register_error2),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        viewModel.registerResponse.removeObservers(viewLifecycleOwner)
                    })
                }
            }
        }

        return binding.root
    }


    fun mockData(){
        binding.editTextRegisterFirstName.setText("emir")
                binding.editTextRegisterLastName.setText("android")
                binding.editTextRegisterEmail.setText("emirandroid1@gmail.com")
                binding.editTextRegisterPassword.setText("emir1234")
                binding.editTextRegisterBirthdate.setText("25/01/2002")
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(1900, 0, 1)
        val minDate = calendar.timeInMillis

        calendar.set(currentYear - 18, currentMonth, currentDay)
        val maxDate = calendar.timeInMillis

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = formatDate(year, monthOfYear, dayOfMonth)
                binding.editTextRegisterBirthdate.setText(selectedDate)
            },
            currentYear, currentMonth, currentDay
        )

        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }


}