package com.emirpetek.wallet_app_android.ui.fragment.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.data.model.enum.PasswordResponse
import com.emirpetek.wallet_app_android.data.request.PasswordChangeRequest
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
                binding.textViewProfileFragmentChangePassword.setOnClickListener { showCustomAlertDialog() }
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


    fun showCustomAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_change_password, null)
        dialogBuilder.setView(dialogView)

        val dialog = dialogBuilder.create()

        dialog.setCanceledOnTouchOutside(false)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        // edittextleri al, butonu aktifleştir, backendi tamamla, responseler için enum


        dialogView.findViewById<Button>(R.id.buttonChangePasswordOk)?.apply {
            setOnClickListener {

                val currentPassword = dialogView.findViewById<EditText>(R.id.editTextAlertChangePasswordCurrent).text.toString().trim()
                val newPassword = dialogView.findViewById<EditText>(R.id.editTextAlertChangePasswordNew).text.toString().trim()
                val newPasswordConfirm = dialogView.findViewById<EditText>(R.id.editTextAlertChangePasswordConfirm).text.toString().trim()

                changePassword(currentPassword, newPassword, newPasswordConfirm, userID,dialog)

            }
        }

    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        newPasswordConfirm: String,
        userID: Long,
        dialog: AlertDialog
    ){
        if (currentPassword.isEmpty() || newPassword.isEmpty() || newPasswordConfirm.isEmpty()){
            Toast.makeText(requireContext(),getString(R.string.fill_all_place),Toast.LENGTH_SHORT).show()
        }else if (newPassword != newPasswordConfirm){
            Toast.makeText(requireContext(),getString(R.string.password_doesnt_match),Toast.LENGTH_SHORT).show()
        }else{

            val passwordChangeRequest = PasswordChangeRequest(userID,currentPassword,newPassword)

            viewModel.changePassword(passwordChangeRequest)
            viewModel.passwordChangeResult.observe(viewLifecycleOwner, Observer { result ->

                result.onSuccess { response ->
                    if (response == PasswordResponse.SUCCESSFUL){
                        Toast.makeText(requireContext(),getString(R.string.password_change_success),Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
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
}














