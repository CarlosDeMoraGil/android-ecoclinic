package com.carlosdmg.ecoclinic.feature.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.app.data.FirebaseAuthentication
import com.carlosdmg.ecoclinic.app.presentation.hide
import com.carlosdmg.ecoclinic.app.presentation.setNumeric
import com.carlosdmg.ecoclinic.app.presentation.show
import com.carlosdmg.ecoclinic.databinding.FragmentSignUpBinding
import com.carlosdmg.ecoclinic.feature.user.domain.User
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth: FirebaseAuthentication by inject()

    private val viewModel: SaveUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpForm()
        setUpNavigation()
    }

    private fun setUpForm() {
        binding.apply {
            signupFrSurnameEdit.setView(R.string.signup_fr_surname)
            signupFrAddressEdit.setView(
                R.string.signup_fr_address,
                R.string.signup_fr_address_hint
            )
            signupFrEmailEdit.setView(R.string.log_fr_email, R.string.log_fr_email_hint)
            signupFrPnEdit.setNumeric()
            sinupFrAgeEdit.setNumeric()
            signupFrPasswdEdit.setView(R.string.log_fr_password)
            signupFrPasswdAuthEdit.setView(
                R.string.log_fr_password,
                R.string.signup_fr_password_auth_hint
            )
        }
    }

    private fun signup() {
        binding.apply {

            val name = signupFrNameEdit.text.toString()
            val surname = signupFrSurnameEdit.getText()
            val address = signupFrAddressEdit.getText()
            val age = sinupFrAgeEdit.text.toString()
            val phoneNumber = signupFrPnEdit.getText().toString()
            val gender = sinupFrGenderEdit.text.toString()
            val email = signupFrEmailEdit.getText()
            val passwd = signupFrPasswdEdit.getText()
            val passwdAuth = signupFrPasswdAuthEdit.getText()

            val passwordsOk = passwd == passwdAuth

            if (passwordsOk) {

                sinupFrErrorPasswd.hide()

                firebaseAuth.createUser(email, passwd) { result ->

                    result.onSuccess {
                        firebaseAuth.getCurrentUserId()?.let { userId ->

                            val user = User(
                                userId,
                                name,
                                surname,
                                address,
                                email,
                                age,
                                gender,
                                phoneNumber
                            )

                            viewModel.saveUser(user)
                            navigateUp()
                        }

                    }

                    result.onFailure { error ->

                        when (error) {
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(
                                    requireContext(),
                                    context?.getString(R.string.signup_fr_user_error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    requireContext(),
                                    "${error.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

            } else {
                sinupFrErrorPasswd.show()
            }

        }

    }

    private fun setUpNavigation() {
        binding.apply {
            signupFrGoback.setOnClickListener {
                navigateToLogin()
            }
            signupFrSignupButton.setOnClickListener {
                signup()
            }
        }
    }


    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun navigateToLogin() {
        findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
    }


}
