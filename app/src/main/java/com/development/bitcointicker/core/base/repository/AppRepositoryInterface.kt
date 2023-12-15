package com.development.bitcointicker.core.base.repository

import com.development.bitcointicker.model.response.bitcoindetail.BitcoinDetailResponse
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface AppRepositoryInterface {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun signOut()
    fun getUUid(): Flow<String>
    fun isUserSaved(): Flow<Boolean>
    fun findCurrentUser(): Flow<FirebaseUser>
    fun getHomeBitcoinList(): Flow<Resource<ArrayList<BitcoinListResponse>>>
    fun getSearchBitcoinList(): Flow<Resource<ArrayList<SearchCoinListResponse>>>
    fun getBitcoinDetail(coinId: String): Flow<Resource<BitcoinDetailResponse>>
    fun addToFavorites(favoriteModel: FavoriteModel): Flow<Resource<Task<Void>>>
    fun deleteFavorites(favoriteModel: FavoriteModel): Flow<Resource<Task<Void>>>
    fun getFavorites(): Flow<Resource<MutableList<DocumentSnapshot>>>
    fun isFavoriteCoin(favoriteModel: FavoriteModel): Flow<Resource<Boolean>>
}