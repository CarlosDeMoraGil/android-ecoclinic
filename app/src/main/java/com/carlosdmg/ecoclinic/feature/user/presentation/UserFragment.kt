package com.carlosdmg.ecoclinic.feature.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.databinding.FragmentLogInBinding

class UserFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

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
        binding()

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun binding(){
        binding.logFrSingupButton.setOnClickListener {
            navigateToSingUp()
        }
    }

    private fun navigateToSingUp() {
        findNavController().navigate(R.id.action_userFragment_to_singupFragment)
    }

}