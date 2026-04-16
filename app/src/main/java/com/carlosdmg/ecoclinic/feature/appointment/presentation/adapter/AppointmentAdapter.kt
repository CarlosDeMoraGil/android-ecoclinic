package com.carlosdmg.ecoclinic.feature.appointment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment

class AppointmentAdapter () : ListAdapter<Appointment, AppointmentViewHolder>(AppointmentDifUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_appointment_list_item, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}