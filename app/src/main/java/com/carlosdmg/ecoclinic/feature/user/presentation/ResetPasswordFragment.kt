package com.carlosdmg.ecoclinic.feature.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.app.data.FirebaseAuthentication
import com.carlosdmg.ecoclinic.databinding.FragmentResetPasswordBinding
import org.koin.android.ext.android.inject

class ResetPasswordFragment : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!

    private val firebaseAuth: FirebaseAuthentication by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }


    private fun setUpView() {
        binding.apply {
            resetPasswdFrToolbar.toolbar.navigationIcon =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_navigation_icon)
            resetPasswdFrToolbar.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            resetPasswdFrToolbar.toolbar.title =
                requireContext().getString(R.string.reset_password_fr_title)
            resetPasswdFrEmailEdit.setView(
                R.string.reset_password_fr_instructions,
                R.string.log_fr_email_hint
            )

            resetPasswdFrContinue.setOnClickListener {
                firebaseAuth.resetPassword(resetPasswdFrEmailEdit.getText())
            }

        }
    }

}