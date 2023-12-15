package com.development.bitcointicker.core.base.repository

import com.development.bitcointicker.model.response.bitcoindetail.BitcoinDetailResponse
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.constants.AppConstants
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.utils.service.BitcoinAPI
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    override fun findCurrentUser(): Flow<FirebaseUser> = flow {
        firebaseAuth.currentUser?.let {
            emit(it)
        }
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

    override fun getBitcoinDetail(coinId: String): Flow<Resource<BitcoinDetailResponse>> = flow {
        emit(Resource.Loading)
        bitcoinAPI.getBitcoinDetail(coinId)?.let {
            emit(Resource.Success(it))
        }

    }.catch {
        emit(Resource.Error(it))
    }

    override fun addToFavorites(favoriteModel: FavoriteModel): Flow<Resource<Task<Void>>> = flow {
        emit(Resource.Loading)

        getUUid().collect {
            val reference = firebaseFirestore.collection(AppConstants.FAVORITE_COINS).document(it)
                .collection(AppConstants.FAVORITE_COIN_TYPE).document(favoriteModel.coinId)
                .set(favoriteModel)
            reference.await()
            emit(Resource.Success(reference))
        }
    }.catch {
        Resource.Error(it)
    }

    override fun isFavoriteCoin(favoriteModel: FavoriteModel): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)

        getUUid().collect { userId ->
            val documentReference = firebaseFirestore.collection(AppConstants.FAVORITE_COINS)
                .document(userId)
                .collection(AppConstants.FAVORITE_COIN_TYPE)
                .document(favoriteModel.coinId)

            try {
                val isFavorite = suspendCoroutine { continuation ->
                    documentReference.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val exists = task.result?.exists() == true
                            continuation.resume(exists)
                        } else {
                            continuation.resumeWithException(
                                task.exception ?: Exception("Firestore error")
                            )
                        }
                    }
                }

                emit(Resource.Success(isFavorite))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }.catch {
        emit(Resource.Error(it))
    }

    override fun deleteFavorites(favoriteModel: FavoriteModel): Flow<Resource<Task<Void>>> = flow {
        emit(Resource.Loading)

        getUUid().collect {
            val reference = firebaseFirestore.collection(AppConstants.FAVORITE_COINS).document(it)
                .collection(AppConstants.FAVORITE_COIN_TYPE).document(favoriteModel.coinId)
                .delete()

            reference.await()
            emit(Resource.Success(reference))
        }
    }.catch {
        Resource.Error(it)
    }

    override fun getFavorites(): Flow<Resource<MutableList<DocumentSnapshot>>> = flow {
        emit(Resource.Loading)

        getUUid()?.collect {
            val data = firebaseFirestore.collection(AppConstants.FAVORITE_COINS).document(it)
                .collection(AppConstants.FAVORITE_COIN_TYPE).get().await()
            emit(Resource.Success(data.documents))
        }
    }.catch {
        emit(Resource.Error(it))
    }
}