package com.carlosdmg.ecoclinic.feature.information.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.app.presentation.MapView
import com.carlosdmg.ecoclinic.app.presentation.NavIntent
import com.carlosdmg.ecoclinic.databinding.FragmentInformationBinding
import com.google.android.gms.maps.SupportMapFragment

class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    private lateinit var navIntent: NavIntent

    private val map = MapView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navIntent = NavIntent(requireContext())

        setUpMap()
        setUpBinding()

    }

    fun setUpMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(map)
    }

    fun setUpBinding(){
        binding.apply {

            inFrPhoneButton.setOnClickListener {
                navigateToDial(R.string.in_fr_phone_number)
            }

        }
    }

    fun navigateToDial(phoneNumber: Int){
        navIntent.openPhoneDial(phoneNumber)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}