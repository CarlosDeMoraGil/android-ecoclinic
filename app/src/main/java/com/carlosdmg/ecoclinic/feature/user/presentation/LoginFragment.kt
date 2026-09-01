package com.carlosdmg.ecoclinic.feature.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.app.data.FirebaseAuthentication
import com.carlosdmg.ecoclinic.databinding.FragmentLogInBinding
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth: FirebaseAuthentication by inject()
    private val navArgs: LoginFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLog()
        setUpView()
    }

    private fun setUpView() {
        binding.apply {
            logFrEditEmail.setView(R.string.log_fr_email, R.string.log_fr_email_hint)
            logFrEditPasswd.setView(
                R.string.log_fr_password,
                R.string.log_fr_passwd_hint, passwordType = true
            )

            logFrSignupButton.setOnClickListener {
                navigateTosignUp()
            }
            logFrLogButton.setOnClickListener {
                login()
            }
            logFrForgotten.setOnClickListener {
                navigateToResetPasswd()
            }
            showHidePassword()
        }
    }

    private fun login() {
        binding.apply {
            if (checkFields()) {
                val email = logFrEditEmail.getText()
                val password = logFrEditPasswd.getText()

                firebaseAuth.login(email, password) { result ->
                    result.onSuccess {
                        findNavController().navigate(R.id.action_loginFragment_to_userFragment)
                    }
                    result.onFailure {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.sigup_fr_login_failed), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.sigup_fr_credentials_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun checkFields(): Boolean {
        return binding.run {
            val fields = listOf(logFrEditEmail, logFrEditPasswd)

            val validationResults = fields.map { field ->
                val isValid = !field.isEmpty()
                val color = if (isValid) R.color.md_theme_onSurface else R.color.md_theme_error
                field.setColor(color)

                isValid
            }

            validationResults.all { it }
        }
    }

    private fun checkLog() {
        val currentUser = firebaseAuth.getCurrentUserId()
        val isSingUp = navArgs.isSignUp

        if (!isSingUp && currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_userFragment)
        }

    }

    private fun showHidePassword() {
        binding.apply {
            var isPasswordVisible = true

            signupFrEye.setOnClickListener {
                isPasswordVisible = !isPasswordVisible

                logFrEditPasswd.setPasswordVisibility(isPasswordVisible)

                if (isPasswordVisible) {
                    signupFrEye.setImageResource(R.drawable.ic_eye_closed)
                } else {
                    signupFrEye.setImageResource(R.drawable.ic_open_eye)
                }
            }
        }
    }


    private fun navigateTosignUp() {
        findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }

    private fun navigateToResetPasswd() {
        findNavController().navigate(R.id.action_loginFragment_to_resetPassword)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}