package com.carlosdmg.ecoclinic.feature.appointment.domain

import org.koin.core.annotation.Single

@Single
class GetAppointmentsUseCase(
    private val appointmentsRepository: AppointmentsRepository
) {

    suspend operator fun invoke(patientId: String): Result<List<Appointment>> {
        return appointmentsRepository.getAppointments(patientId)
    }
}