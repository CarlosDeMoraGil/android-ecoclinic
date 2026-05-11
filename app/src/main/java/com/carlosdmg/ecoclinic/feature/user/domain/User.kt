package com.carlosdmg.ecoclinic.feature.user.domain

data class User(
    val userId: String,
    val name: String,
    val surname: String,
    val address: String,
    val email: String,
    val age: String,
    val gender: String,
    val phoneNumber: String,
    val appointments: List<String> = emptyList()
)