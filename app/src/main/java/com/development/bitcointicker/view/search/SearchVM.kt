package com.development.bitcointicker.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(private val repositoryImpl: AppRepositoryImpl) : ViewModel(),
    SearchContracts.ViewModel {

    private val _uiStateData = MutableSharedFlow<Resource<ArrayList<SearchCoinListResponse>>>()
    override val uiStateData: SharedFlow<Resource<ArrayList<SearchCoinListResponse>>> =
        _uiStateData.asSharedFlow()

    override fun invokeAction(action: SearchContracts.Action) {
        when (action) {
            is SearchContracts.Action.GetSearchBitcoinList -> getSearchCoinList()
        }
    }
    private fun getSearchCoinList() = viewModelScope.launch {
        repositoryImpl.getSearchBitcoinList().collect {
            _uiStateData.emit(it)
        }
    }
}