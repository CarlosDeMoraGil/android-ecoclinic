package com.carlosdmg.ecoclinic.feature.appointment.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.carlosdmg.ecoclinic.databinding.ViewAppointmentListItemBinding
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment

class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ViewAppointmentListItemBinding.bind(view)
    fun bind(item: Appointment) {
        binding.apply {
            apptVwTypeData.text = item.type
            apptVwTimestampData.text = item.timestamp
            apptVwDetailsData.text = item.details
        }
    }
}

