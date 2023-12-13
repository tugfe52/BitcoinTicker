package com.development.bitcointicker.view.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.development.bitcointicker.R
import com.development.bitcointicker.utils.extensions.CustomAlertDialog
import kotlinx.coroutines.launch

typealias ActivityInflater <T> = (LayoutInflater) -> T

abstract class BaseActivity<VB : ViewBinding>(private val inflate: ActivityInflater<VB>) :
    AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    protected abstract fun mViewModel(): ViewModel

    private var _binding: VB? = null

    val binding get() = _binding!!
    private lateinit var mAlertDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        viewModel = mViewModel()
        setupActivity()
    }
    protected abstract fun setupActivity()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    fun coroutinesLauncherWithCreatedState(action: suspend () -> Unit) {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                action.invoke()
            }
        }
    }

    fun <T> LiveData<T>.observed(owner: LifecycleOwner, f: (T?) -> Unit) {
        this.observe(owner, Observer<T> { t -> f(t) })
    }

    fun <T> LiveData<T>.observedNonNull(owner: LifecycleOwner, f: (T) -> Unit) {
        this.observe(owner, Observer<T> { t -> t?.let(f) })
    }

    private fun showProgress() {

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.progress_layout, null)

        val mBuilder = AlertDialog.Builder(this)
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

    fun showError(throwable: Throwable) {
        CustomAlertDialog.showCustomAlertDialog(
            this,
            isCancelable = false,
            content = throwable.message.orEmpty(),
            backButtonText = "Kapat",
            backClickListener = {
                finish()
            },
            continueButtonText = "Tekrar Dene",
            continueClickListener = {},
            closeClickListener = {})
    }
}