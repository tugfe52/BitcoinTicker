package com.development.bitcointicker.view.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.bitcoindetail.BitcoinDetailResponse
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.utils.constants.AppConstants.FAVORITE_COIN_ID
import com.development.bitcointicker.utils.constants.AppConstants.FAVORITE_COIN_IMAGE
import com.development.bitcointicker.utils.constants.AppConstants.FAVORITE_COIN_NAME
import com.development.bitcointicker.utils.constants.AppConstants.FAVORITE_COIN_PERCENTAGE
import com.development.bitcointicker.utils.constants.AppConstants.FAVORITE_COIN_PRICE
import com.development.bitcointicker.utils.extensions.orZeroPoint
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.coindetail.CoinDetailContracts
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteVM @Inject constructor(private val repositoryImpl: AppRepositoryImpl) : ViewModel(),
    CoinDetailContracts.FavoriteViewModel {

    private val _uiStateData = MutableSharedFlow<Resource<ArrayList<FavoriteModel>>>()
    override val uiStateData: SharedFlow<Resource<ArrayList<FavoriteModel>>> =
        _uiStateData.asSharedFlow()

    override fun invokeAction(action: CoinDetailContracts.Action) {
        when (action) {
            is CoinDetailContracts.Action.GetAllFavorites -> getAllFavorites()
            else -> {}
        }
    }

    private fun getAllFavorites() = viewModelScope.launch {
        repositoryImpl.getFavorites().collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    val favorites = arrayListOf<FavoriteModel>()
                    resource.data.forEach {
                        favorites.add(
                            FavoriteModel(
                                it.data?.get(FAVORITE_COIN_ID).toString(),
                                it.data?.get(FAVORITE_COIN_NAME).toString(),
                                it.data?.get(FAVORITE_COIN_PERCENTAGE).toString().toDoubleOrNull().orZeroPoint(),
                                it.data?.get(FAVORITE_COIN_PRICE).toString().toDoubleOrNull().orZeroPoint(),
                                it.data?.get(FAVORITE_COIN_IMAGE).toString(),
                            )
                        )
                    }
                    _uiStateData.emit(Resource.Success(favorites))
                }

                is Resource.Loading -> _uiStateData.emit(Resource.Loading)
                is Resource.Error -> _uiStateData.emit(Resource.Error(resource.throwable))
            }
        }
    }
}