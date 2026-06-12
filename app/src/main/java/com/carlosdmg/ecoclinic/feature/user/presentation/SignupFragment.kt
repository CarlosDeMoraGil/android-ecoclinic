package com.carlosdmg.ecoclinic.feature.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.app.data.FirebaseAuthentication
import com.carlosdmg.ecoclinic.app.presentation.hide
import com.carlosdmg.ecoclinic.app.presentation.setColor
import com.carlosdmg.ecoclinic.app.presentation.setVisibility
import com.carlosdmg.ecoclinic.databinding.FragmentSignUpBinding
import com.carlosdmg.ecoclinic.feature.user.domain.User
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth: FirebaseAuthentication by inject()
    private val viewModel: SaveUserViewModel by viewModel()
    private var selectedGender: String = ""

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
        setSpinner()
    }

    private fun setUpForm() {
        binding.apply {
            signupFrNameEdit.setView(R.string.signup_fr_name, widthF = R.dimen.image_xxxl)
            signupFrSurnameEdit.setView(R.string.signup_fr_surname)
            signupFrAddressEdit.setView(
                R.string.signup_fr_address,
                R.string.signup_fr_address_hint
            )
            signupFrEmailEdit.setView(R.string.log_fr_email, R.string.log_fr_email_hint)
            signupFrPnEdit.setView(
                R.string.signup_fr_phone,
                widthF = R.dimen.image_xxxl,
                numeric = true
            )
            signupFrAgeEdit.setView(
                R.string.signup_fr_age,
                widthF = R.dimen.image_xl,
                numeric = true
            )
            signupFrPasswdEdit.setView(
                R.string.log_fr_password,
                R.string.signup_fr_passwd_hint,
                passwordType = true
            )
            signupFrPasswdAuthEdit.setView(
                R.string.log_fr_password,
                R.string.signup_fr_password_auth_hint, passwordType = true
            )
            showHidePassword()
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

    private fun signup() {
        binding.apply {

            if (!checkFields()) return@apply

            val email = signupFrEmailEdit.getText()
            val passwd = signupFrPasswdEdit.getText()
            val passwdAuth = signupFrPasswdAuthEdit.getText()
            val phoneNumber = signupFrPnEdit.getText()

            firebaseAuth.validatePhoneNumber(phoneNumber)

            val credentialsValidation =
                firebaseAuth.validateCredentials(email, passwd, passwdAuth, phoneNumber)

            if (credentialsValidation[2]) {

                signupFrErrorPasswd.hide()
                signupFrErrorEmail.hide()
                signupFrPnEdit.setColor(R.color.md_theme_onSurface)


                firebaseAuth.createUser(email, passwd) { result ->

                    result.onSuccess {

                        val name = signupFrNameEdit.getText()
                        val surname = signupFrSurnameEdit.getText()
                        val address = signupFrAddressEdit.getText()
                        val age = signupFrAgeEdit.getText()
                        val gender = selectedGender

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
                            navigateToLogin()
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

                            is FirebaseAuthWeakPasswordException -> {
                                Toast.makeText(
                                    requireContext(),
                                    context?.getString(R.string.signup_fr_weak_password_error),
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
                var i = -1

                credentialsValidation.forEach { credential ->
                    i++
                    when (i) {
                        0 -> signupFrErrorPasswd.setVisibility(credential)
                        1 -> signupFrErrorEmail.setVisibility(credential)
                        3 -> if (!credential) {
                            signupFrPnEdit.setColor(R.color.md_theme_error)
                        } else {
                            signupFrPnEdit.setColor(R.color.md_theme_onSurface)
                        }
                    }

                }
            }

        }

    }

    private fun checkFields(): Boolean {
        return binding.run {
            val textFields = listOf(
                signupFrNameEdit, signupFrSurnameEdit, signupFrAddressEdit,
                signupFrAgeEdit, signupFrPnEdit, signupFrEmailEdit,
                signupFrPasswdEdit, signupFrPasswdAuthEdit
            )

            val areTextsValid = textFields.map { field ->
                val isValid = !field.isEmpty()
                val color = if (isValid) R.color.md_theme_onSurface else R.color.md_theme_error
                field.setColor(color)
                isValid
            }.all { it }

            val isGenderValid = selectedGender.isNotEmpty()
            val genderColor =
                if (isGenderValid) R.color.md_theme_onSurface else R.color.md_theme_error

            signupFrGenderTitle.setColor(genderColor)

            areTextsValid && isGenderValid
        }
    }

    private fun showHidePassword() {
        binding.apply {
            var isPasswordVisible = true

            signupFrEye.setOnClickListener {
                isPasswordVisible = !isPasswordVisible

                signupFrPasswdEdit.setPasswordVisibility(isPasswordVisible)
                signupFrPasswdAuthEdit.setPasswordVisibility(isPasswordVisible)

                if (isPasswordVisible) {
                    signupFrEye.setImageResource(R.drawable.ic_eye_closed)
                } else {
                    signupFrEye.setImageResource(R.drawable.ic_open_eye)
                }
            }
        }
    }

    private fun setSpinner() {
        val genderOptions = resources.getStringArray(R.array.signup_fr_spinner_options)

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.signupFrGenderSpinner.apply {
            this.adapter = adapter

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedGender = genderOptions[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    selectedGender = ""
                }
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            SignupFragmentDirections.actionSignupFragmentToLoginFragment(
                true
            )
        )
    }


}
