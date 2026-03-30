package com.carlosdmg.ecoclinic.feature.appointment.domain

data class Appointment (
    private val id: String,
    private val details: String,
    private val timestamp: String,
    private val patient: Patient
) {
}