package com.development.bitcointicker.utils.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.development.bitcointicker.core.base.repository.AppRepositoryImpl
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.utils.constants.AppConstants
import com.development.bitcointicker.utils.extensions.orZeroPoint
import com.development.bitcointicker.utils.notification.NotificationImp
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.home.HomeVM
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MyWorkerImpl @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val repositoryImpl: AppRepositoryImpl
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        return try {

            var coinList = arrayListOf<BitcoinListResponse>()
            var isPriceChanged = false

            repositoryImpl.getHomeBitcoinList().collect { resource ->

                when (resource) {
                    is Resource.Success -> {
                        coinList = resource.data
                    }

                    else -> {}
                }
            }

            repositoryImpl.getFavorites().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        if (resource.data.isNotEmpty()) {
                            resource.data.forEach { fav ->
                                coinList.forEach { coin ->
                                    if (fav.data?.get(AppConstants.FAVORITE_COIN_NAME)
                                            .toString() == coin.name && fav.data?.get(AppConstants.FAVORITE_COIN_PRICE)
                                            .toString()
                                            .toDoubleOrNull().orZeroPoint() != coin.currentPrice
                                    ) {
                                        isPriceChanged = true
                                    }
                                }
                            }
                            if (isPriceChanged) {
                                NotificationImp.showNotification(
                                    applicationContext, NOTIFICATION_MESSAGE,
                                    NOTIFICATION_TITLE
                                )
                            }
                        }
                    }
                    else -> {}
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val NOTIFICATION_TITLE = "Bildirim"
        const val NOTIFICATION_MESSAGE = "Favori listende değişiklikler var!"
    }

}