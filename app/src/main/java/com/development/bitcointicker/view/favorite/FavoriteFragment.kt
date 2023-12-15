package com.development.bitcointicker.view.favorite

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.development.bitcointicker.databinding.FragmentFavoriteBinding
import com.development.bitcointicker.utils.notification.NotificationImp
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.base.BaseFragment
import com.development.bitcointicker.view.coindetail.CoinDetailContracts
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private val favoriteVm: FavoriteVM by viewModels()
    private val adapter by lazy { FavoritesAdapter() }
    override fun mViewModel(): ViewModel = favoriteVm

    override fun setupView() {

        with (binding) {
            rvFavorites.adapter = adapter
            favoriteVm.invokeAction(CoinDetailContracts.Action.GetAllFavorites)
            observeFavoritesData()

            toolbarFavorites.setOnClickListener {
                NotificationImp.showNotification(requireContext(), "Bildirim", "Başlık")
            }
        }
    }
    private fun observeFavoritesData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            favoriteVm.uiStateData.collect { resource ->

                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()
                        adapter.updateFavorites(resource.data)
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }
            }
        }
    }
}