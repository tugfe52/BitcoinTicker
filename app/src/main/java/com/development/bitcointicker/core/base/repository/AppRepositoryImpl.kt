package com.development.bitcointicker.core.base.repository

import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.utils.service.BitcoinAPI
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val bitcoinAPI: BitcoinAPI
) : AppRepositoryInterface {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(firebaseAuth.signInWithEmailAndPassword(email, password).await()))
    }.catch {
        emit(Resource.Error(it))
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> =
        flow<Resource<AuthResult>> {
            emit(Resource.Loading)
            emit(
                Resource.Success(
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                )
            )
        }.catch { Resource.Error(it) }

    override fun signOut() = firebaseAuth.signOut()

    override fun getUUid(): Flow<String> = flow {
        firebaseAuth.currentUser?.uid?.let {
            emit(it)
        }
    }

    override fun isUserSaved(): Flow<Boolean> = flow {
        emit(firebaseAuth.currentUser != null)
    }

    override fun findCurrentUser(): Flow<Resource<FirebaseUser>> = flow {
        emit(Resource.Loading)
        firebaseAuth.currentUser?.let {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error(it))
    }

    override fun getHomeBitcoinList(): Flow<Resource<ArrayList<BitcoinListResponse>>> = flow {
        emit(Resource.Loading)
        bitcoinAPI.getHomeCoinList()?.let {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error(it))
    }
    override fun getSearchBitcoinList(): Flow<Resource<ArrayList<SearchCoinListResponse>>> = flow {
        emit(Resource.Loading)
        bitcoinAPI.getSearchCoinList()?.let {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error(it))
    }
}