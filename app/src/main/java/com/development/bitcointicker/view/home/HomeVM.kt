package com.development.bitcointicker.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import com.google.firebase.auth.FirebaseUser
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

    private val _currentUserData = MutableLiveData<FirebaseUser>()
    override val currentUserData: LiveData<FirebaseUser> = _currentUserData

    override fun invokeAction(action: HomeContracts.Action) {
        when (action) {
            is HomeContracts.Action.GetBitcoinList -> getBitcoinList()
            is HomeContracts.Action.GetCurrentUser -> getCurrentUser()
            is HomeContracts.Action.Logout -> repositoryImpl.signOut()
        }
    }

    private fun getBitcoinList() = viewModelScope.launch {
        repositoryImpl.getHomeBitcoinList().collect {
            _uiStateData.emit(it)
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        repositoryImpl.findCurrentUser().collect { resource ->
            _currentUserData.value = resource
        }
    }
}