package com.carlosdmg.ecoclinic.feature.appointment.data.remote

import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun AppointmentDbModel.toModel(): Appointment {
    return Appointment(this.id, this.details, formatFirestoreDate(this.timestamp), this.patientId)
}


fun formatFirestoreDate(timestamp: Timestamp): String {
    val date = timestamp.toDate()

    val sdf = SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm", Locale("es", "ES"))

    return sdf.format(date)
}

