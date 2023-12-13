package com.development.bitcointicker.utils.extensions

import com.development.bitcointicker.R
import com.google.android.material.textview.MaterialTextView

fun MaterialTextView.setRateChangeColor(price: String){
    if (price.contains("-")) this.context.getColor(R.color.decrease_red)
    else this.context.getColor(R.color.increase_green)
}