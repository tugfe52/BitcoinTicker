package com.development.bitcointicker.utils.extensions

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View

fun String.onlyApplyColor(targetText: String, color: Int, sizeMultiplier: Float): SpannableString {
    val spannableString = SpannableString(this)

    val startIndex = this.indexOf(targetText)
    val endIndex = startIndex + targetText.length

    if (startIndex != -1) {
        val colorSpan = ForegroundColorSpan(color)
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

        val sizeSpan = RelativeSizeSpan(sizeMultiplier)
        spannableString.setSpan(sizeSpan, startIndex, endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
    }
    return spannableString
}

fun View.show() {
    visibility = View.VISIBLE
}
fun View.gone() {
    visibility = View.GONE
}