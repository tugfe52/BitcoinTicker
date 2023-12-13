package com.development.bitcointicker.view.home

import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import kotlinx.coroutines.flow.SharedFlow

sealed class HomeContracts {
    interface ViewModel {
        val uiStateData: SharedFlow<Resource<ArrayList<BitcoinListResponse>>>
        fun invokeAction(action: Action)
    }
    sealed class Action {
        object GetBitcoinList : Action()
    }
}