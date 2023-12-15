package com.development.bitcointicker.core.base.navigation

import androidx.navigation.NavController
import com.development.bitcointicker.view.auth.login.LoginFragmentDirections
import com.development.bitcointicker.view.home.HomeFragmentDirections

interface NavigationDelegate {
    fun navigateToRegister(navController: NavController)
    fun navigateToCoinDetail(navController: NavController, coinId: String)
}

class NavigateDelegateImpl : NavigationDelegate {
    override fun navigateToRegister(navController: NavController) {
        LoginFragmentDirections.actionLoginFragmentToRegisterFragment().apply {
            navController.navigate(this)
        }
    }

    override fun navigateToCoinDetail(navController: NavController, coinId: String) {
        HomeFragmentDirections.actionHomeFragmentToCoinDetailFragment(coinId).apply {
            navController.navigate(this)
        }
    }
}