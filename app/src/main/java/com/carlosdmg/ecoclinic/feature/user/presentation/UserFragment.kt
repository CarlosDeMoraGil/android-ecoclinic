package com.carlosdmg.ecoclinic.feature.user.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.app.data.FirebaseAuthentication
import com.carlosdmg.ecoclinic.databinding.FragmentLogInBinding
import com.carlosdmg.ecoclinic.databinding.FragmentUserBinding
import com.carlosdmg.ecoclinic.feature.user.domain.User
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModel()
    private val firebaseAuth: FirebaseAuthentication by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserById(firebaseAuth.getCurrentUserId())

        setUpObserver()

    }

    private fun setUpObserver(){
        val observer = Observer<UserViewModel.UiState>{ uiState ->
            uiState.let {
                bind(it.user)
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }

    private fun bind(user: User?){
        binding.userFrWelcome.text = user?.name
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}