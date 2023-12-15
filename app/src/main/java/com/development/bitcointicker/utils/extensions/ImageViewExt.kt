package com.development.bitcointicker.utils.extensions

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.development.bitcointicker.R

fun ImageView.setRateImageDrawable(price: String, context: Context) {
    if (price.contains("-")) {
        this.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.bitcoin_increase
            )
        )
    } else {
        this.setImageDrawable(
            ContextCompat.getDrawable(
                context,
                R.drawable.bitcoin_decrease
            )
        )
    }
}