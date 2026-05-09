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
import com.carlosdmg.ecoclinic.databinding.FragmentSingUpBinding
import com.carlosdmg.ecoclinic.feature.user.domain.User
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingupFragment : Fragment() {

    private var _binding: FragmentSingUpBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth: FirebaseAuthentication by inject()

    private val viewModel: SaveUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSingUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpForm()
        setUpNavigation()
    }

    private fun setUpForm() {
        binding.apply {
            singupFrSurnameEdit.setView(R.string.singup_fr_surname)
            singupFrAddressEdit.setView(
                R.string.singup_fr_address,
                R.string.singup_fr_address_hint
            )
            singupFrEmailEdit.setView(R.string.log_fr_email, R.string.log_fr_email_hint)
            singupFrPnEdit.setNumeric()
            sinupFrAgeEdit.setNumeric()
            singupFrPasswdEdit.setView(R.string.log_fr_password)
            singupFrPasswdAuthEdit.setView(
                R.string.log_fr_password,
                R.string.singup_fr_password_auth_hint
            )
        }
    }

    private fun singup() {
        binding.apply {

            val name = singupFrNameEdit.text.toString()
            val surname = singupFrSurnameEdit.getText()
            val address = singupFrAddressEdit.getText()
            val age = sinupFrAgeEdit.text.toString()
            val phoneNumber = singupFrPnEdit.getText().toString()
            val gender = sinupFrGenderEdit.text.toString()
            val email = singupFrEmailEdit.getText()
            val passwd = singupFrPasswdEdit.getText()
            val passwdAuth = singupFrPasswdAuthEdit.getText()

            val passwordsOk = passwd == passwdAuth

            if (passwordsOk){

                sinupFrErrorPasswd.hide()

                firebaseAuth.createUser(email, passwd) { result ->

                    result.onSuccess {
                        val user = User(firebaseAuth.getCurrentUserId(), name, surname, address, email, age, gender,phoneNumber)

                        viewModel.saveUser(user)
                        navigateUp()

                    }

                    result.onFailure { error ->

                        when (error) {
                            is FirebaseAuthUserCollisionException -> {
                                Toast.makeText(requireContext(), context?.getString(R.string.singup_fr_user_error), Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(requireContext(), "${error.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            }else{
                sinupFrErrorPasswd.show()
            }

        }

    }

    private fun setUpNavigation() {
        binding.apply {
            singupFrGoback.setOnClickListener {
                navigateToLogin()
            }
            singupFrSingupButton.setOnClickListener {
                singup()
            }
        }
    }


    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun navigateToLogin() {
        findNavController().navigate(SingupFragmentDirections.actionSingupFragmentToLoginFragment())
    }


}
