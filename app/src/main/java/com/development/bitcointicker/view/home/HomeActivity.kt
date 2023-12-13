package com.development.bitcointicker.view.home

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.development.bitcointicker.R
import com.development.bitcointicker.databinding.ActivityHomeBinding
import com.development.bitcointicker.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val homeVm: HomeVM by viewModels()
    override fun mViewModel(): ViewModel = homeVm

    override fun setupActivity() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)
    }

}