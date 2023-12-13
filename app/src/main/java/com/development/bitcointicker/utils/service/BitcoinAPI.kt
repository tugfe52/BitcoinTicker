package com.development.bitcointicker.utils.service

import com.development.bitcointicker.model.response.bitcoindetail.BitcoinDetailResponse
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.constants.AppConstants
import retrofit2.http.GET
import retrofit2.http.Path

interface BitcoinAPI {
    @GET(AppConstants.ADD_URL + AppConstants.COIN_SEARCH_LISTS)
    suspend fun getSearchCoinList(): ArrayList<SearchCoinListResponse>

    @GET(AppConstants.ADD_URL + AppConstants.HOME_COIN_LIST)
    suspend fun getHomeCoinList(): ArrayList<BitcoinListResponse>

    @GET(AppConstants.ADD_URL + AppConstants.SEARCH_COIN_WITH_ID)
    suspend fun getBitcoinDetail(@Path("id") id: String): ArrayList<BitcoinDetailResponse>
}