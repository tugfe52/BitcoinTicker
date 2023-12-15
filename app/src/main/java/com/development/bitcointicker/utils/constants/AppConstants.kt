package com.development.bitcointicker.utils.constants

object AppConstants {
    const val BASE_URL = "https://api.coingecko.com/api/v3/coins/"
    const val ADD_URL = "markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false/"
    const val COIN_SEARCH_LISTS = "coins/list"
    const val HOME_COIN_LIST ="coins/markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false"
    const val SEARCH_COIN_WITH_ID ="{id}"
    const val FavoriteCoins = "FavoriteCoins"
    const val FavoriteCoinType = "FavoriteCoinType"
    const val FAVORITE_COIN_ID = "coinId"
    const val FAVORITE_COIN_NAME = "coinName"
    const val FAVORITE_COIN_PERCENTAGE = "percentage"
    const val FAVORITE_COIN_PRICE = "price"
    const val FAVORITE_COIN_IMAGE = "image"
    const val NOTIFICATION_CHANNEL = "FavoritesNotificationsChannel"
    const val NOTIFICATION_ID = "FavoritesNotifications"
}