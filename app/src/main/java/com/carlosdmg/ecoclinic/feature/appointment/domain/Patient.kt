package com.carlosdmg.ecoclinic.feature.appointment.domain

data class Patient(
    private val id: String,
    private val name: String,
    private val surname: String,
    private val email: String,
    private val phone: String,
    private val address: String,
    private val age: String,
    private val gender: String
) {
}