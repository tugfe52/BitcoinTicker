package com.development.bitcointicker.core.base.repository

import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AppRepositoryInterface {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun signOut()
    fun getUUid(): Flow<String>
    fun isUserSaved(): Flow<Boolean>
    fun findCurrentUser(): Flow<Resource<FirebaseUser>>
    fun getHomeBitcoinList(): Flow<Resource<ArrayList<BitcoinListResponse>>>
    fun getSearchBitcoinList(): Flow<Resource<ArrayList<SearchCoinListResponse>>>
}