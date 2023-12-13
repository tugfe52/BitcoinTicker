package com.development.bitcointicker.utils.extensions

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validateInputs(
    isEmail: Boolean,
    errorText: String = "Geçerli bir e-mail adresi giriniz",
    isPassword: Boolean = false
): Boolean {

    val inputText = this.editText?.text.toString().trim()

    return if (isEmail && !Patterns.EMAIL_ADDRESS.matcher(inputText).matches()) {
        this.error = errorText
        this.isErrorEnabled = true
        false
    } else if (isPassword && this.editText?.text?.length.orZero()< 8) {
        this.error = "Parolanız en az 8 karakterden oluşmalıdır"
        this.isErrorEnabled = true
        this.errorIconDrawable = null
        false
    } else {
        this.error = null
        this.isErrorEnabled = false
        true
    }
}
