package com.development.bitcointicker.utils

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.development.bitcointicker.R

class ProgressUtils(private val activity: Activity?) {

    private lateinit var mAlertDialog: AlertDialog

    fun showProgress() {

        val mDialogView = LayoutInflater.from(activity).inflate(R.layout.progress_layout, null)

        val mBuilder = AlertDialog.Builder(activity)
            .setView(mDialogView)
            .setCancelable(false)


        mBuilder?.let {
            mAlertDialog = it.show()
        }

        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun hideProgress() {
        if (::mAlertDialog.isInitialized) {
            mAlertDialog.dismiss()
        }
    }
}