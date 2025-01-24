package com.emirpetek.wallet_app_android.ui.fragment.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.databinding.FragmentForgotPasswordVerifyCodeBinding
import com.emirpetek.wallet_app_android.util.ManageBottomBarVisibility


class ForgotPasswordVerifyCodeFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordVerifyCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordVerifyCodeBinding.inflate(inflater,container,false)


        ManageBottomBarVisibility(requireActivity()).hideBottomNav()


        val email = arguments?.getString("email")
        val id = arguments?.getLong("userID")!!

        val editTextList = listOf(binding.editTextVerifyEmail1, binding.editTextVerifyEmail2, binding.editTextVerifyEmail3, binding.editTextVerifyEmail4)

        val bundle = Bundle().apply { putLong("userID",id) }
        Log.e("id: ", id.toString())


        binding.textViewVeriyEmailText.append(email)

        editTextList.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1) {
                        // Eğer son EditText'e ulaşıldıysa ve tüm EditText'ler doluysa kontrol yap
                        if (index == editTextList.size - 1 && areAllEditTextsFilled(editTextList)) {
                            findNavController().navigate(R.id.action_forgotPasswordVerifyCodeFragment_to_forgotPasswordChangePasswordFragment,bundle)
                        }
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }

        return binding.root
    }

    fun areAllEditTextsFilled(editTextList: List<EditText>): Boolean {
        return editTextList.all { it.text.toString().isNotEmpty() }
    }

}