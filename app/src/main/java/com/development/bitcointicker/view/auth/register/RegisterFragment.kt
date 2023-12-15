package com.development.bitcointicker.view.auth.register

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.development.bitcointicker.databinding.FragmentRegisterBinding
import com.development.bitcointicker.model.response.auth.AuthModel
import com.development.bitcointicker.utils.extensions.validateInputs
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.auth.AuthVM
import com.development.bitcointicker.view.auth.sign.AuthContracts
import com.development.bitcointicker.view.base.BaseFragment
import com.development.bitcointicker.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val authVm: AuthVM by viewModels()
    override fun mViewModel(): AuthVM = authVm
    override fun setupView() {

        with(binding) {

            imgRegisterBack.setOnClickListener {
                findNavController().popBackStack()
            }

            tvRegisterLoginNavigate.setOnClickListener {
                findNavController().popBackStack()
            }

            btnRegister.setOnClickListener {
                validateInputs()
            }
        }
    }

    private fun validateInputs() = with(binding) {

        val email = etRegisterEmail.text.toString()
        val password = etRegisterPassword.text.toString()

        if (!authVm.checkInputEntries(email, password)) {
            Toast.makeText(requireContext(), "Lütfen bilgilerinizi giriniz", Toast.LENGTH_SHORT)
                .show()
        } else if (!tlRegisterEmail.validateInputs(true) || !tlRegisterPassword.validateInputs(
                isEmail = false,
                isPassword = true
            )
        ) {

        } else {
            authVm.invokeAction(AuthContracts.Action.SendRegister(AuthModel(email, password)))
            observeRegisterData()
        }
    }

    private fun observeRegisterData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            authVm.uiStateData.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()
                        Toast.makeText(requireActivity(), "Kayıt başarılı", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(requireContext(), HomeActivity::class.java))
                        requireActivity().finish()

                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }

            }
        }
    }
}