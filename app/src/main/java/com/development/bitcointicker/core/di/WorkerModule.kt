package com.development.bitcointicker.core.di

import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.utils.service.BitcoinAPI
import com.development.bitcointicker.view.home.HomeVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {

    @Singleton
    @Provides
    fun provideHomeVm(repositoryImpl: AppRepositoryImpl): HomeVM {
        return HomeVM(repositoryImpl)
    }
    @Provides
    @Singleton
    fun provideAppRepositoryImpl(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        bitcoinAPI: BitcoinAPI
    ): AppRepositoryImpl {
        return AppRepositoryImpl(firebaseAuth, firebaseFirestore, bitcoinAPI)
    }
}