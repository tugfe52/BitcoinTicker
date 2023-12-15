package com.development.bitcointicker.view.search

import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import kotlinx.coroutines.flow.SharedFlow

sealed class SearchContracts {
    interface ViewModel {
        val uiStateData: SharedFlow<Resource<ArrayList<SearchCoinListResponse>>>
        fun invokeAction(action: Action)
    }
    sealed class Action {
        object GetSearchBitcoinList : Action()
    }
}