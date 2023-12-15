package com.development.bitcointicker.utils.extensions

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.development.bitcointicker.R
import com.development.bitcointicker.databinding.CustomAlertDialogBinding

object CustomAlertDialog {

    private lateinit var dialogBuilder: AlertDialog.Builder

    fun showCustomAlertDialog(
        context: Context,
        isCancelable: Boolean = true,
        content: String,
        fromChooseSeat: Boolean = false,
        seatNumber: Int = 0,
        isSingleButton: Boolean = false,
        backButtonText: String = "Geri Dön",
        backClickListener: (() -> Unit)? = null,
        continueButtonText: String = "Devam Et",
        continueClickListener: (() -> Unit)? = null,
        closeButtonText: String = "Kapat",
        closeClickListener: (() -> Unit)? = null,
    ) {

        dialogBuilder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        val binding = CustomAlertDialogBinding.inflate(LayoutInflater.from(context))

        dialogBuilder.setCancelable(isCancelable)
        dialogBuilder.setView(binding.root)
        val alertDialog = dialogBuilder.create()

        with(binding) {

            btnContinue.text = continueButtonText
            btnBack.text = backButtonText
            btnClose.text = closeButtonText

            if (fromChooseSeat && seatNumber != 0) {
                val registerText = content
                customAlertContent.text = registerText.onlyApplyColor(
                    seatNumber.toString(),
                    ContextCompat.getColor(context, R.color.alert_orange_color),
                    1.5f
                )

            } else {
                customAlertContent.text = content
            }

            if (isSingleButton) {
                llSingleButton.show()
                llMultipleButton.gone()
                btnClose.setOnClickListener {

                    if (closeClickListener != null) {
                        closeClickListener()
                        alertDialog.dismiss()
                    } else {
                        alertDialog.dismiss()
                    }

                }
            } else {
                llSingleButton.gone()
                llMultipleButton.show()

                btnBack.setOnClickListener {
                    if (backClickListener != null) {
                        backClickListener()
                        alertDialog.dismiss()
                    } else {
                        alertDialog.dismiss()
                    }
                }

                btnContinue.setOnClickListener {
                    if (continueClickListener != null) {
                        continueClickListener()
                        alertDialog.dismiss()
                    } else {
                        alertDialog.dismiss()
                    }
                }
            }

        }
        alertDialog.show()
    }
}
