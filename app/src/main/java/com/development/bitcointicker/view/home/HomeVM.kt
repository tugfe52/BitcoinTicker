package com.development.bitcointicker.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val repositoryImpl: AppRepositoryImpl) : ViewModel(),
    HomeContracts.ViewModel {

    private val _uiStateData = MutableSharedFlow<Resource<ArrayList<BitcoinListResponse>>>()
    override val uiStateData: SharedFlow<Resource<ArrayList<BitcoinListResponse>>> =
        _uiStateData.asSharedFlow()

    override fun invokeAction(action: HomeContracts.Action) {
        when (action) {
            is HomeContracts.Action.GetBitcoinList -> getBitcoinList()
        }
    }

    private fun getBitcoinList() = viewModelScope.launch {
        repositoryImpl.getHomeBitcoinList().collect {
            _uiStateData.emit(it)
        }
    }
}