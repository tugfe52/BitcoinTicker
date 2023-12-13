package com.development.bitcointicker.core.base.navigation

import androidx.navigation.NavController
import com.development.bitcointicker.view.auth.login.LoginFragmentDirections

interface NavigationDelegate {
    fun navigateToRegister(navController: NavController)
}

class NavigateDelegateImpl : NavigationDelegate {
    override fun navigateToRegister(navController: NavController) {
        LoginFragmentDirections.actionLoginFragmentToRegisterFragment().apply {
            navController.navigate(this)
        }
    }

}