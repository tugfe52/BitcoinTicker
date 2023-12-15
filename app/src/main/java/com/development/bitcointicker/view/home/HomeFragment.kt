package com.development.bitcointicker.view.home

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.development.bitcointicker.core.base.navigation.NavigateDelegateImpl
import com.development.bitcointicker.core.base.navigation.NavigationDelegate
import com.development.bitcointicker.databinding.FragmentHomeBinding
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.auth.AuthActivity
import com.development.bitcointicker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    NavigationDelegate by NavigateDelegateImpl() {

    private val homeVm: HomeVM by viewModels()
    private val bitcoinListAdapter by lazy { BitcoinListAdapter() }
    override fun mViewModel(): ViewModel = homeVm

    override fun setupView() {

        with(binding) {

            ivLogout.setOnClickListener {
                homeVm.invokeAction(HomeContracts.Action.Logout)
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }

            rvHome.adapter = bitcoinListAdapter
            homeVm.invokeAction(HomeContracts.Action.GetBitcoinList)
            observeBitcoinList()
            homeVm.invokeAction(HomeContracts.Action.GetCurrentUser)
            observeUserData()

            bitcoinListAdapter.coinClick = {
                navigateToCoinDetail(findNavController(), it)
            }
        }
    }

    private fun observeUserData() = with(binding) {

        homeVm.currentUserData.observe(viewLifecycleOwner) { resource ->
            tvHomeWelcome.text = "Merhaba,\n${resource.email}"
        }
    }

    private fun observeBitcoinList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            homeVm.uiStateData.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()
                        bitcoinListAdapter.updateBitcoinList(resource.data)
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }

            }
        }
    }
}