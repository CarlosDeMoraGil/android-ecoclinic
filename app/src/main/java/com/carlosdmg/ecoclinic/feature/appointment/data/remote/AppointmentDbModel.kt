package com.carlosdmg.ecoclinic.feature.appointment.data.remote

import com.google.firebase.Timestamp

data class AppointmentDbModel(
    val id: String = "",
    val details: String = " ",
    val timestamp: Timestamp = Timestamp.now(),
    val patientId: String = " "
) {
}