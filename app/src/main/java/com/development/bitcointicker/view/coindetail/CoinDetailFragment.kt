package com.development.bitcointicker.view.coindetail

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.development.bitcointicker.R
import com.development.bitcointicker.databinding.FragmentCoinDetailBinding
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.utils.extensions.TimerOptionsPopUp
import com.development.bitcointicker.utils.extensions.gone
import com.development.bitcointicker.utils.extensions.orZeroPoint
import com.development.bitcointicker.utils.extensions.setRateChangeColor
import com.development.bitcointicker.utils.extensions.setRateImageDrawable
import com.development.bitcointicker.utils.extensions.show
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinDetailFragment :
    BaseFragment<FragmentCoinDetailBinding>(FragmentCoinDetailBinding::inflate),
    TimerOptionsClickListener {

    private val coinDetailVM: CoinDetailVM by viewModels()
    private val args: CoinDetailFragmentArgs by navArgs()
    override fun mViewModel(): ViewModel = coinDetailVM

    override fun setupView() {

        with(binding) {

            toolbarFragmentDetail.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            coinDetailVM.invokeAction(CoinDetailContracts.Action.GetBitcoinDetail(args.coinId))
            observeDetailData()
            observeFavoriteData()
        }
    }

    private fun observeFavoriteData() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            coinDetailVM.favoriteData.collect { resource ->

                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()

                        if (resource.data.isComplete) {
                            Toast.makeText(
                                requireContext(),
                                requireContext().getString(R.string.add_favorite_success_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                requireContext().getString(R.string.add_favorite_error_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            coinDetailVM.deleteFavoriteData.collect { resource ->

                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()

                        if (resource.data.isComplete) {
                            Toast.makeText(
                                requireContext(),
                                requireContext().getString(R.string.delete_favorite_success_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                requireContext().getString(R.string.delete_favorite_error_text),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }

            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            coinDetailVM.isFavoriteData.collect { resource ->

                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()

                        if (resource.data) {
                            ivDetailFavorite.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_favorite
                            )
                            ivDetailFavorite.isChecked = true
                        } else {
                            ivDetailFavorite.background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_unfavorite
                            )

                            ivDetailFavorite.isChecked = false
                        }
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }
            }
        }
    }

    private fun observeDetailData() = with(binding) {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            coinDetailVM.uiStateData.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()
                        resource.data?.let { response ->

                            val priceChangePercentage =
                                response.marketData?.priceChangePercentage24h
                            Glide.with(requireContext()).load(response.image?.small)
                                .into(ivToolbarIcon)
                            tvToolbarTitle.text =
                                "${response.name} - (${response.symbol.orEmpty().uppercase()})"
                            tvCoinCurrentPrice.text =
                                "$ " + String.format("%.3f", response.marketData?.currentPrice?.usd)
                            tvCoinCurrentRate.text =
                                "(${String.format("%.2f", priceChangePercentage) + "%)"}"
                            tvCoinCurrentRate.setRateChangeColor(priceChangePercentage.toString())
                            ivDetailRateIcon.setRateImageDrawable(
                                priceChangePercentage.toString(),
                                requireContext()
                            )
                            tvDetailHashing.text = "Hashing Algorithm: ${response.hashingAlgorithm}"

                            response.description?.let { description ->
                                tvDetailDescription.show()
                                tvDetailDescription.text = description.en
                            } ?: run {
                                tvDetailDescription.gone()
                            }

                            cvRefresh.setOnClickListener {
                                TimerOptionsPopUp.showTimerPopUp(
                                    requireContext(),
                                    isCancelable = true,
                                    this@CoinDetailFragment
                                )
                            }

                            val model = FavoriteModel(
                                response.id.orEmpty(),
                                response.name.orEmpty(),
                                priceChangePercentage.orZeroPoint(),
                                response.marketData?.currentPrice?.usd.orZeroPoint(),
                                response.image?.small.orEmpty()
                            )

                            coinDetailVM.invokeAction(CoinDetailContracts.Action.IsFavorite(model))

                            ivDetailFavorite.setOnCheckedChangeListener { buttonView, isChecked ->

                                if (isChecked) {
                                    buttonView.background = ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_favorite
                                    )
                                    buttonView.isChecked = true
                                    coinDetailVM.invokeAction(
                                        CoinDetailContracts.Action.AddToFavorite(
                                            model
                                        )
                                    )
                                } else {
                                    buttonView.background = ContextCompat.getDrawable(
                                        requireContext(),
                                        R.drawable.ic_unfavorite
                                    )
                                    buttonView.isChecked = false
                                    coinDetailVM.invokeAction(
                                        CoinDetailContracts.Action.DeleteFavorite(
                                            model
                                        )
                                    )
                                }
                            }

                        }
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }
            }
        }
    }

    override fun onOptionSelected(selectedValue: Int) {

        CoroutineScope(Dispatchers.Main).launch {
            coinDetailVM.invokeAction(CoinDetailContracts.Action.GetBitcoinDetail(args.coinId))
            //observeDetailData()
            delay(selectedValue * 1000L)
        }
    }
}