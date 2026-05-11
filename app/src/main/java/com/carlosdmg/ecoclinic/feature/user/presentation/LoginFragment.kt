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
        binding()
        setUpLogin()
    }

    private fun setUpLogin() {
        binding.apply {
            logFrLogButton.setOnClickListener {
                login()
            }
        }
    }


    private fun login() {
        binding.apply {
            val email = logFrEditEmail.getText()
            val password = logFrEditPasswd.getText()

            firebaseAuth.login(email, password) { result ->
                result.onSuccess {
                    findNavController().navigate(R.id.action_loginFragment_to_userFragment)
                }
                result.onFailure {
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkLog() {
        val currentUser = firebaseAuth.getCurrentUserId()
        val isSingUp = navArgs.isSignUp

        if (!isSingUp && currentUser != null) {
            findNavController().navigate(R.id.action_loginFragment_to_userFragment)
        }

    }


    private fun binding() {
        binding.apply {
            logFrEditEmail.setView(R.string.log_fr_email, R.string.log_fr_email_hint)
            logFrEditPasswd.setView(R.string.log_fr_password)

            logFrSignupButton.setOnClickListener {
                navigateTosignUp()
            }
        }
    }

    private fun navigateTosignUp() {
        findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }

}