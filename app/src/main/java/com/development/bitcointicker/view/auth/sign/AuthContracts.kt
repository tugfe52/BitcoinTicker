package com.development.bitcointicker.view.auth.sign

import androidx.lifecycle.LiveData
import com.development.bitcointicker.model.response.auth.AuthModel
import com.development.bitcointicker.utils.resource.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

sealed class AuthContracts {
    interface ViewModel {
        val uiStateData: SharedFlow<Resource<AuthResult>>
        val uiEventData: LiveData<Event>
        fun invokeAction(action: Action)
        val uiSavableState: SavableState.UserIsLogged
    }
    sealed class Action {
        data class SendLogin(val authModel: AuthModel) : Action()
        data class SendRegister(val authModel: AuthModel) : Action()
    }
    sealed class Event {
    }

    sealed class SavableState {
        data class UserIsLogged(val isLogged: Boolean)
    }
}