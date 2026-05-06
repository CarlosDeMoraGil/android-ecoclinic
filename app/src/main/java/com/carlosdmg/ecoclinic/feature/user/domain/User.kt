package com.carlosdmg.ecoclinic.feature.user.domain

import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment

data class User(
    val userId: String,
    val name: String,
    val surname: String,
    val address: String,
    val email: String,
    val password: String,
    val age: String,
    val gender: String,
    val phoneNumber: String,
    val appointments: List<Appointment>
)