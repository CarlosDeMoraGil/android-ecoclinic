package com.carlosdmg.ecoclinic.feature.appointment.domain

interface AppointmentsRepository {

    suspend fun getAppointments(patientId: String): Result<List<Appointment>>

}