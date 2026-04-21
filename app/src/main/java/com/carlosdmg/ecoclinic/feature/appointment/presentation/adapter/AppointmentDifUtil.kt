package com.carlosdmg.ecoclinic.feature.appointment.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment

class AppointmentDifUtil : DiffUtil.ItemCallback<Appointment>() {

    override fun areItemsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Appointment, newItem: Appointment): Boolean {
        return oldItem == newItem
    }


}