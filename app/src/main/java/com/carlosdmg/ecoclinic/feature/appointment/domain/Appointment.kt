package com.carlosdmg.ecoclinic.feature.appointment.domain

data class Appointment (
    val id: String,
    val details: String,
    val timestamp: String,
    val patientId: String,
    val type: String
) {
}