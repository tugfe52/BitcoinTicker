package com.development.bitcointicker.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.auth.AuthModel
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.auth.sign.AuthContracts
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl
) : ViewModel(), AuthContracts.ViewModel {

    private val _uiStateData = MutableSharedFlow<Resource<AuthResult>>()
    override val uiStateData = _uiStateData.asSharedFlow()

    private val _uiEventData = MutableLiveData<AuthContracts.Event>()
    override val uiEventData: LiveData<AuthContracts.Event> = _uiEventData

    override fun invokeAction(action: AuthContracts.Action) {
        when (action) {
            is AuthContracts.Action.SendLogin -> sendLogin(action.authModel)
            is AuthContracts.Action.SendRegister -> registerUser(action.authModel)
        }
    }

    fun checkInputEntries(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun sendLogin(authModel: AuthModel) = viewModelScope.launch {
        repositoryImpl.loginUser(authModel.email, authModel.password).collect {
            _uiStateData.emit(it)
        }
    }
    private fun registerUser(authModel: AuthModel) = viewModelScope.launch {
        repositoryImpl.registerUser(authModel.email, authModel.password).collect {
            _uiStateData.emit(it)
        }
    }

    override val uiSavableState: AuthContracts.SavableState.UserIsLogged =
        AuthContracts.SavableState.UserIsLogged(isUserLogged())

    fun isUserLogged(): Boolean {

        var isLogged = false
        viewModelScope.launch {
            repositoryImpl.isUserSaved().collect {
                isLogged = it
            }
        }
        return isLogged
    }
}