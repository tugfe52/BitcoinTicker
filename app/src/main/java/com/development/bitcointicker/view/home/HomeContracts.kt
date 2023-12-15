package com.development.bitcointicker.view.home

import androidx.lifecycle.LiveData
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.SharedFlow

sealed class HomeContracts {
    interface ViewModel {
        val uiStateData: SharedFlow<Resource<ArrayList<BitcoinListResponse>>>
        val currentUserData: LiveData<FirebaseUser>
        fun invokeAction(action: Action)
    }
    sealed class Action {
        object GetBitcoinList : Action()
        object GetCurrentUser : Action()

        object Logout: Action()
    }
}