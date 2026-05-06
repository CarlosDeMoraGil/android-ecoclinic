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
import com.carlosdmg.ecoclinic.databinding.FragmentSingUpBinding
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import org.koin.android.ext.android.inject

class SingupFragment : Fragment() {

    private var _binding: FragmentSingUpBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth: FirebaseAuthentication by inject()

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
        binding.singupFrSurnameEdit.setView(R.string.singup_fr_surname)
        binding.singupFrAddressEdit.setView(
            R.string.singup_fr_address,
            R.string.singup_fr_address_hint
        )
        binding.singupFrEmailEdit.setView(R.string.log_fr_email, R.string.log_fr_email_hint)
        binding.singupFrPasswdEdit.setView(R.string.log_fr_password)
        binding.singupFrPasswdAuthEdit.setView(
            R.string.log_fr_password,
            R.string.singup_fr_password_auth_hint
        )
    }

    private fun singup() {
        binding.apply {

            val email = singupFrEmailEdit.getText()
            val passwd = singupFrPasswdEdit.getText()
            val passwdAuth = singupFrPasswdAuthEdit.getText()

            val passwordsOk = passwd == passwdAuth

            if (passwordsOk){

                sinupFrErrorPasswd.visibility = View.GONE

                firebaseAuth.createUser(email, passwd) { result ->

                    result.onSuccess {
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
                sinupFrErrorPasswd.visibility = View.VISIBLE
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
        findNavController().navigate(SingupFragmentDirections.actionSingupFragmentToUserFragment(null))
    }


}
