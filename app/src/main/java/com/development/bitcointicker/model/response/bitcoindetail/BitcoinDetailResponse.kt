package com.development.bitcointicker.model.response.bitcoindetail


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BitcoinDetailResponse(
    @SerializedName("additional_notices")
    val additionalNotices: List<String?>?,
    @SerializedName("asset_platform_id")
    val assetPlatformId: String?,
    @SerializedName("block_time_in_minutes")
    val blockTimeInMinutes: Int?,
    @SerializedName("categories")
    val categories: List<String?>?,
    @SerializedName("country_origin")
    val countryOrigin: String?,
    @SerializedName("description")
    val description: Description?,
    @SerializedName("detail_platforms")
    val detailPlatforms: DetailPlatforms?,
    @SerializedName("genesis_date")
    val genesisDate: String?,
    @SerializedName("hashing_algorithm")
    val hashingAlgorithm: String?,
    @SerializedName("ico_data")
    val icoData: IcoData?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("market_data")
    val marketData: MarketData?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("platforms")
    val platforms: Platforms?,
    @SerializedName("preview_listing")
    val previewListing: Boolean?,
    @SerializedName("public_notice")
    val publicNotice: String?,
    @SerializedName("sentiment_votes_down_percentage")
    val sentimentVotesDownPercentage: Double?,
    @SerializedName("sentiment_votes_up_percentage")
    val sentimentVotesUpPercentage: Double?,
    @SerializedName("symbol")
    val symbol: String?,
    @SerializedName("watchlist_portfolio_users")
    val watchlistPortfolioUsers: Int?,
    @SerializedName("web_slug")
    val webSlug: String?
) : Parcelable {
    @Parcelize
    data class Description(
        @SerializedName("en")
        val en: String?
    ) : Parcelable

    @Parcelize
    data class DetailPlatforms(
        @SerializedName("")
        val x: X?
    ) : Parcelable {
        @Parcelize
        data class X(
            @SerializedName("contract_address")
            val contractAddress: String?,
            @SerializedName("decimal_place")
            val decimalPlace: String?
        ) : Parcelable
    }

    @Parcelize
    data class IcoData(
        @SerializedName("accepting_currencies")
        val acceptingCurrencies: String?,
        @SerializedName("amount_for_sale")
        val amountForSale: String?,
        @SerializedName("base_pre_sale_amount")
        val basePreSaleAmount: String?,
        @SerializedName("base_public_sale_amount")
        val basePublicSaleAmount: Int?,
        @SerializedName("bounty_detail_url")
        val bountyDetailUrl: String?,
        @SerializedName("country_origin")
        val countryOrigin: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("hardcap_amount")
        val hardcapAmount: String?,
        @SerializedName("hardcap_currency")
        val hardcapCurrency: String?,
        @SerializedName("ico_end_date")
        val icoEndDate: String?,
        @SerializedName("ico_start_date")
        val icoStartDate: String?,
        @SerializedName("kyc_required")
        val kycRequired: Boolean?,
        @SerializedName("links")
        val links: Links?,
        @SerializedName("pre_sale_available")
        val preSaleAvailable: String?,
        @SerializedName("pre_sale_end_date")
        val preSaleEndDate: String?,
        @SerializedName("pre_sale_ended")
        val preSaleEnded: Boolean?,
        @SerializedName("pre_sale_start_date")
        val preSaleStartDate: String?,
        @SerializedName("quote_pre_sale_amount")
        val quotePreSaleAmount: String?,
        @SerializedName("quote_pre_sale_currency")
        val quotePreSaleCurrency: String?,
        @SerializedName("quote_public_sale_amount")
        val quotePublicSaleAmount: Double?,
        @SerializedName("quote_public_sale_currency")
        val quotePublicSaleCurrency: String?,
        @SerializedName("short_desc")
        val shortDesc: String?,
        @SerializedName("softcap_amount")
        val softcapAmount: String?,
        @SerializedName("softcap_currency")
        val softcapCurrency: String?,
        @SerializedName("total_raised")
        val totalRaised: String?,
        @SerializedName("total_raised_currency")
        val totalRaisedCurrency: String?,
        @SerializedName("whitelist_available")
        val whitelistAvailable: String?,
        @SerializedName("whitelist_end_date")
        val whitelistEndDate: String?,
        @SerializedName("whitelist_start_date")
        val whitelistStartDate: String?,
        @SerializedName("whitelist_url")
        val whitelistUrl: String?
    ) : Parcelable {
        @Parcelize
        class Links : Parcelable
    }

    @Parcelize
    data class Image(
        @SerializedName("large")
        val large: String?,
        @SerializedName("small")
        val small: String?,
        @SerializedName("thumb")
        val thumb: String?
    ) : Parcelable

    @Parcelize
    data class Links(
        @SerializedName("announcement_url")
        val announcementUrl: List<String?>?,
        @SerializedName("bitcointalk_thread_identifier")
        val bitcointalkThreadIdentifier: String?,
        @SerializedName("blockchain_site")
        val blockchainSite: List<String?>?,
        @SerializedName("chat_url")
        val chatUrl: List<String?>?,
        @SerializedName("facebook_username")
        val facebookUsername: String?,
        @SerializedName("homepage")
        val homepage: List<String?>?,
        @SerializedName("official_forum_url")
        val officialForumUrl: List<String?>?,
        @SerializedName("repos_url")
        val reposUrl: ReposUrl?,
        @SerializedName("subreddit_url")
        val subredditUrl: String?,
        @SerializedName("telegram_channel_identifier")
        val telegramChannelIdentifier: String?,
        @SerializedName("twitter_screen_name")
        val twitterScreenName: String?
    ) : Parcelable {
        @Parcelize
        data class ReposUrl(
            @SerializedName("bitbucket")
            val bitbucket: List<String?>?,
            @SerializedName("github")
            val github: List<String?>?
        ) : Parcelable
    }

    @Parcelize
    data class MarketData(
        @SerializedName("ath")
        val ath: CoinPrizeType?,
        @SerializedName("ath_change_percentage")
        val athChangePercentage: CoinPrizeType?,
        @SerializedName("atl")
        val atl: CoinPrizeType?,
        @SerializedName("atl_change_percentage")
        val atlChangePercentage: CoinPrizeType?,
        @SerializedName("circulating_supply")
        val circulatingSupply: Double?,
        @SerializedName("current_price")
        val currentPrice: CoinPrizeType?,
        @SerializedName("fdv_to_tvl_ratio")
        val fdvToTvlRatio: String?,
        @SerializedName("fully_diluted_valuation")
        val fullyDilutedValuation: CoinPrizeType?,
        @SerializedName("high_24h")
        val high24h: CoinPrizeType?,
        @SerializedName("last_updated")
        val lastUpdated: String?,
        @SerializedName("low_24h")
        val low24h: CoinPrizeType?,
        @SerializedName("market_cap")
        val marketCap: CoinPrizeType?,
        @SerializedName("market_cap_change_24h")
        val marketCapChange24h: Double?,
        @SerializedName("market_cap_change_24h_in_currency")
        val marketCapChange24hInCurrency: CoinPrizeType?,
        @SerializedName("market_cap_change_percentage_24h")
        val marketCapChangePercentage24h: Double?,
        @SerializedName("market_cap_change_percentage_24h_in_currency")
        val marketCapChangePercentage24hInCurrency: CoinPrizeType?,
        @SerializedName("market_cap_rank")
        val marketCapRank: Int?,
        @SerializedName("max_supply")
        val maxSupply: String?,
        @SerializedName("mcap_to_tvl_ratio")
        val mcapToTvlRatio: String?,
        @SerializedName("price_change_24h")
        val priceChange24h: Double?,
        @SerializedName("price_change_24h_in_currency")
        val priceChange24hInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_14d")
        val priceChangePercentage14d: Double?,
        @SerializedName("price_change_percentage_14d_in_currency")
        val priceChangePercentage14dInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_1h_in_currency")
        val priceChangePercentage1hInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_1y")
        val priceChangePercentage1y: Double?,
        @SerializedName("price_change_percentage_1y_in_currency")
        val priceChangePercentage1yInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_200d")
        val priceChangePercentage200d: Double?,
        @SerializedName("price_change_percentage_200d_in_currency")
        val priceChangePercentage200dInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_24h")
        val priceChangePercentage24h: Double?,
        @SerializedName("price_change_percentage_24h_in_currency")
        val priceChangePercentage24hInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_30d")
        val priceChangePercentage30d: Double?,
        @SerializedName("price_change_percentage_30d_in_currency")
        val priceChangePercentage30dInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_60d")
        val priceChangePercentage60d: Double?,
        @SerializedName("price_change_percentage_60d_in_currency")
        val priceChangePercentage60dInCurrency: CoinPrizeType?,
        @SerializedName("price_change_percentage_7d")
        val priceChangePercentage7d: Double?,
        @SerializedName("price_change_percentage_7d_in_currency")
        val priceChangePercentage7dInCurrency: CoinPrizeType?,
        @SerializedName("roi")
        val roi: Roi?,
        @SerializedName("total_supply")
        val totalSupply: Double?,
        @SerializedName("total_value_locked")
        val totalValueLocked: String?,
        @SerializedName("total_volume")
        val totalVolume: CoinPrizeType?
    ) : Parcelable {



        @Parcelize
        data class Roi(
            @SerializedName("currency")
            val currency: String?,
            @SerializedName("percentage")
            val percentage: Double?,
            @SerializedName("times")
            val times: Double?
        ) : Parcelable
    }

    @Parcelize
    data class Platforms(
        @SerializedName("")
        val x: String?
    ) : Parcelable
}