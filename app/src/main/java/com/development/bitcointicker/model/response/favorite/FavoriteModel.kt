package com.development.bitcointicker.model.response.favorite

data class FavoriteModel(
    val coinId: String,
    val coinName: String,
    val percentage: Double,
    val price: Double,
    val image: String
)