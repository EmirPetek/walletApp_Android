    package com.emirpetek.wallet_app_android.ui.fragment.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emirpetek.wallet_app_android.R
import com.emirpetek.wallet_app_android.databinding.FragmentHomeBinding
import com.emirpetek.wallet_app_android.ui.viewmodel.home.HomeViewModel

    class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()
        private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.tv.setText("hello id: " + viewModel.getUserID(requireContext()).toString())

        return binding.root
    }
}