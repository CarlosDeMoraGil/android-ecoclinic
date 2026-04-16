package com.carlosdmg.ecoclinic.feature.appointment.data

import com.carlosdmg.ecoclinic.feature.appointment.data.remote.AppointmentsFirestoreRemoteDataSource
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.carlosdmg.ecoclinic.feature.appointment.domain.AppointmentsRepository
import org.koin.core.annotation.Single

@Single
class AppointmentsDataRepository(private val remoteDataSource: AppointmentsFirestoreRemoteDataSource) :
    AppointmentsRepository {

    override suspend fun getAppointments(patientId: String): Result<List<Appointment>> {
        return remoteDataSource.getAppointments(patientId)
    }

}