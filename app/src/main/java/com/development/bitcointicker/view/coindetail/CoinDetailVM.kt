package com.development.bitcointicker.view.coindetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.bitcoindetail.BitcoinDetailResponse
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.utils.resource.Resource
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailVM @Inject constructor(private val repositoryImpl: AppRepositoryImpl) : ViewModel(),
    CoinDetailContracts.ViewModel {

    private val _uiStateData = MutableSharedFlow<Resource<BitcoinDetailResponse>>()
    override val uiStateData: SharedFlow<Resource<BitcoinDetailResponse>> = _uiStateData.asSharedFlow()

    private val _favoriteData = MutableSharedFlow<Resource<Task<Void>>>()
    override val favoriteData: SharedFlow<Resource<Task<Void>>> =
        _favoriteData.asSharedFlow()

    private val _deleteFavoriteData = MutableSharedFlow<Resource<Task<Void>>>()
    override val deleteFavoriteData: SharedFlow<Resource<Task<Void>>> =
        _deleteFavoriteData.asSharedFlow()

    private val _isFavoriteCoinData = MutableSharedFlow<Resource<Boolean>>()
    override val isFavoriteData: SharedFlow<Resource<Boolean>> =
        _isFavoriteCoinData.asSharedFlow()

    override fun invokeAction(action: CoinDetailContracts.Action) {
        when (action) {
            is CoinDetailContracts.Action.GetBitcoinDetail -> getBitcoinDetail(action.coinId)
            is CoinDetailContracts.Action.AddToFavorite -> addToFavorite(action.favoriteModel)
            is CoinDetailContracts.Action.DeleteFavorite -> deleteFavorite(action.favoriteModel)
            is CoinDetailContracts.Action.IsFavorite -> isCoinFavorite(action.favoriteModel)
            else -> {}
        }
    }

    private fun getBitcoinDetail(coinId: String) = viewModelScope.launch {
        repositoryImpl.getBitcoinDetail(coinId).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    _uiStateData.emit(Resource.Success(resource.data))
                }

                is Resource.Loading -> _uiStateData.emit(Resource.Loading)
                is Resource.Error -> _uiStateData.emit(Resource.Error(resource.throwable))
            }
        }
    }
    private fun addToFavorite(favoriteModel: FavoriteModel) = viewModelScope.launch {
        repositoryImpl.addToFavorites(favoriteModel).collect {resource->

            when (resource) {
                is Resource.Success -> {
                    _favoriteData.emit(Resource.Success(resource.data))
                }

                is Resource.Loading -> _favoriteData.emit(Resource.Loading)
                is Resource.Error -> _favoriteData.emit(Resource.Error(resource.throwable))
            }
        }
    }

    private fun deleteFavorite(favoriteModel: FavoriteModel) = viewModelScope.launch {
        repositoryImpl.deleteFavorites(favoriteModel).collect {resource->

            when (resource) {
                is Resource.Success -> {
                    _deleteFavoriteData.emit(Resource.Success(resource.data))
                }

                is Resource.Loading -> _deleteFavoriteData.emit(Resource.Loading)
                is Resource.Error -> _deleteFavoriteData.emit(Resource.Error(resource.throwable))
            }
        }
    }

    private fun isCoinFavorite(favoriteModel: FavoriteModel) = viewModelScope.launch {
        repositoryImpl.isFavoriteCoin(favoriteModel).collect {resource->

            when (resource) {
                is Resource.Success -> {
                    _isFavoriteCoinData.emit(Resource.Success(resource.data))
                }

                is Resource.Loading -> _deleteFavoriteData.emit(Resource.Loading)
                is Resource.Error -> _deleteFavoriteData.emit(Resource.Error(resource.throwable))
            }
        }
    }
}