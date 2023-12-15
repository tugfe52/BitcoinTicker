package com.development.bitcointicker.view.coindetail

import com.development.bitcointicker.model.response.bitcoindetail.BitcoinDetailResponse
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.utils.resource.Resource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.SharedFlow

sealed class CoinDetailContracts {
    interface ViewModel {
        val uiStateData: SharedFlow<Resource<BitcoinDetailResponse>>
        val favoriteData: SharedFlow<Resource<Task<Void>>>
        val deleteFavoriteData: SharedFlow<Resource<Task<Void>>>
        val isFavoriteData: SharedFlow<Resource<Boolean>>
        fun invokeAction(action: Action)
    }

    interface FavoriteViewModel {
        val uiStateData: SharedFlow<Resource<ArrayList<FavoriteModel>>>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        data class GetBitcoinDetail(val coinId: String) : Action()
        data class AddToFavorite(val favoriteModel: FavoriteModel) : Action()
        data class DeleteFavorite(val favoriteModel: FavoriteModel) : Action()
        data class IsFavorite(val favoriteModel: FavoriteModel) : Action()
        object GetAllFavorites : Action()
    }
}