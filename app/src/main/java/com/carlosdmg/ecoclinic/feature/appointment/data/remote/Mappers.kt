package com.carlosdmg.ecoclinic.feature.appointment.data.remote

import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

fun AppointmentDbModel.toModel(): Appointment {
    return Appointment(this.id, this.details, formatFirestoreDate(this.timestamp), this.patientId, this.type)
}


fun formatFirestoreDate(timestamp: Timestamp): String {
    val date = timestamp.toDate()

    val locale = Locale.forLanguageTag("es-ES")
    val sdf = SimpleDateFormat("dd 'de' MMMM 'de' yyyy 'a las' HH:mm", locale)

    return sdf.format(date)
}

