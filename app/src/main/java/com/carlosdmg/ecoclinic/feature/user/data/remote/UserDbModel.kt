package com.carlosdmg.ecoclinic.feature.user.data.remote

import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment

class UserDbModel(
    val userId: String = "",
    val name: String = "",
    val surname: String = "",
    val address: String = "",
    val email: String = "",
    val age: String = "",
    val gender: String = "",
    val phoneNumber: String = ""
)