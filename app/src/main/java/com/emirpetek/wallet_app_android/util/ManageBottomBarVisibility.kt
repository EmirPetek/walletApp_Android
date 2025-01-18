package com.emirpetek.wallet_app_android.util

import android.app.Activity
import android.view.View
import com.emirpetek.wallet_app_android.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ManageBottomBarVisibility ( val requireActivity: Activity
) {

    fun showBottomNav() {
        val bottomNav =
            requireActivity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav?.visibility = View.VISIBLE
    }

    fun hideBottomNav() {
        val bottomNav =
            requireActivity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav?.visibility = View.GONE
    }
}