package com.development.bitcointicker.view.base

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.development.bitcointicker.R
import com.development.bitcointicker.utils.ProgressUtils
import com.development.bitcointicker.utils.extensions.CustomAlertDialog
import kotlinx.coroutines.launch

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val inflate: Inflate<VB>) : Fragment() {

    private lateinit var viewModel: ViewModel
    protected abstract fun mViewModel(): ViewModel

    private lateinit var mAlertDialog: AlertDialog
    private var _binding: VB? = null
    val binding get() = requireNotNull(_binding)

    private val progress by lazy {
        ProgressUtils(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = mViewModel()
        setupView()
    }

    protected abstract fun setupView()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun coroutinesLauncherWithCreatedState(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                action.invoke()
            }
        }
    }

    fun <T> LiveData<T>.observed(owner: LifecycleOwner, f: (T?) -> Unit) {
        this.observe(owner) { t -> f(t) }
    }

    fun <T> LiveData<T>.observedNonNull(owner: LifecycleOwner, f: (T) -> Unit) {
        this.observe(owner) { t -> t?.let(f) }
    }

    fun showProgress() {

        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.progress_layout, null)

        val mBuilder = AlertDialog.Builder(requireContext())
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
        Log.e("ErrorMessage", throwable.message.orEmpty())
        CustomAlertDialog.showCustomAlertDialog(
            requireContext(),
            isCancelable = false,
            content = throwable.message.orEmpty(),
            backButtonText = "Kapat",
            backClickListener = {
                requireActivity().finish()
            },
            continueButtonText = "Tekrar Dene",
            continueClickListener = {},
            closeClickListener = {})
    }
}