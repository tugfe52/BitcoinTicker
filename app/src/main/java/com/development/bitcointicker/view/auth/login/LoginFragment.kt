package com.development.bitcointicker.view.auth.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.development.bitcointicker.core.base.navigation.NavigateDelegateImpl
import com.development.bitcointicker.core.base.navigation.NavigationDelegate
import com.development.bitcointicker.databinding.FragmentLoginBinding
import com.development.bitcointicker.model.response.auth.AuthModel
import com.development.bitcointicker.utils.extensions.validateInputs
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.auth.AuthVM
import com.development.bitcointicker.view.auth.sign.AuthContracts
import com.development.bitcointicker.view.base.BaseFragment
import com.development.bitcointicker.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    NavigationDelegate by NavigateDelegateImpl() {

    private val authVm: AuthVM by viewModels()
    override fun mViewModel(): ViewModel = authVm
    override fun setupView() {

        with(binding) {

            if(authVm.isUserLogged()){
                navigateToHome()
            }
            tvLoginRegisterNavigate.setOnClickListener {
                navigateToRegister(findNavController())
            }

            btnLogin.setOnClickListener {
                validateInputs()
            }
        }
    }
    private fun validateInputs() = with(binding) {

        val email = etLoginEmail.text.toString()
        val password = etLoginPassword.text.toString()

        if (!authVm.checkInputEntries(email, password)) {
            Toast.makeText(requireContext(), "Lütfen bilgilerinizi giriniz", Toast.LENGTH_SHORT)
                .show()
        } else if (!tlLoginEmail.validateInputs(true) || !tlLoginEmail.validateInputs(
                isEmail = false,
                isPassword = true
            )
        ) {

        } else {
            authVm.invokeAction(AuthContracts.Action.SendLogin(AuthModel(email, password)))
            observeLoginData()
        }
    }
    private fun observeLoginData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            authVm.uiStateData.collect { resource->
                when(resource){
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()
                        Toast.makeText(requireActivity(), "Giriş başarılı", Toast.LENGTH_SHORT).show()
                        navigateToHome()
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }

            }
        }
    }

    fun navigateToHome(){
        startActivity(Intent(requireContext(), HomeActivity::class.java))
        requireActivity().finish()
    }
}