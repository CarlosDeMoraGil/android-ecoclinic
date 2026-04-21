package com.carlosdmg.ecoclinic.feature.appointment.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.databinding.FragmentAppointmentBinding
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.carlosdmg.ecoclinic.feature.appointment.presentation.adapter.AppointmentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppointmentFragment : Fragment() {

    private var _binding: FragmentAppointmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AppointmentsViewModel by viewModel()

    private lateinit var appointmentAdapter: AppointmentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadAppointments("PAT_1")
        setUpView()
        setUpObserver()

    }

    private fun setUpObserver() {
        val observer = Observer<AppointmentsViewModel.UiState> { uiState ->
            uiState.appointments.let { bindData(it) }
        }

        viewModel.uiState.observe(viewLifecycleOwner, observer)
    }


    private fun setUpView() {
        binding.apply {
            apptFrRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            appointmentAdapter = AppointmentAdapter()
            apptFrRecyclerView.adapter = appointmentAdapter
            apptFrToolbar.toolbar.title = getString(R.string.in_fr_toolbar_title)
        }
    }

    private fun bindData(appointments: List<Appointment>?){
        appointmentAdapter.submitList(appointments)
        checkAppointments(appointments)
    }

    private fun checkAppointments(appointments: List<Appointment>?){
        if(appointments?.isEmpty() == true){
            binding.apptFrCard.visibility = View.VISIBLE
        }else{
            binding.apptFrCard.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}