package com.development.bitcointicker.view.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.development.bitcointicker.databinding.FragmentHomeBinding
import com.development.bitcointicker.model.response.auth.AuthModel
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.auth.sign.AuthContracts
import com.development.bitcointicker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeVm: HomeVM by viewModels()
    private val bitcoinListAdapter by lazy { BitcoinListAdapter() }
    override fun mViewModel(): ViewModel = homeVm

    override fun setupView() {

        with(binding){
            rvHome.adapter = bitcoinListAdapter
            homeVm.invokeAction(HomeContracts.Action.GetBitcoinList)
            observeBitcoinList()
        }
    }

    private fun observeBitcoinList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            homeVm.uiStateData.collect { resource->
                when(resource){
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