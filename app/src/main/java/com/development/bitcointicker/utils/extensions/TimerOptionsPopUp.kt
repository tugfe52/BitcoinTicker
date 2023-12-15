package com.development.bitcointicker.utils.extensions

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.development.bitcointicker.R
import com.development.bitcointicker.databinding.ItemReloadTimePopUpBinding
import com.development.bitcointicker.view.coindetail.TimerOptionsClickListener
import com.development.bitcointicker.view.coindetail.RefreshTimerOptionsAdapter

object TimerOptionsPopUp {

    private lateinit var dialogBuilder: AlertDialog.Builder
    private val adapter by lazy { RefreshTimerOptionsAdapter() }

    fun showTimerPopUp(
        context: Context,
        isCancelable: Boolean = true,
        chooseClickListener: TimerOptionsClickListener? = null,
    ) {

        dialogBuilder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        val binding = ItemReloadTimePopUpBinding.inflate(LayoutInflater.from(context))

        dialogBuilder.setCancelable(isCancelable)
        dialogBuilder.setView(binding.root)
        val alertDialog = dialogBuilder.create()

        with(binding) {

            ivPopUpClose.setOnClickListener { alertDialog.dismiss() }

            rvReloadTimer.adapter = adapter
            adapter.updateOptionsList(arrayListOf(60, 120, 180, 240))

            adapter.optionsClick = {
                chooseClickListener?.onOptionSelected(it)
                alertDialog.dismiss()
            }
        }


        alertDialog.show()
    }
}