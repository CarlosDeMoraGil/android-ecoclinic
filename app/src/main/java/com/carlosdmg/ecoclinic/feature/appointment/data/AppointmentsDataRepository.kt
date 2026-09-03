package com.carlosdmg.ecoclinic.feature.appointment.data

import com.carlosdmg.ecoclinic.app.domain.ErrorApp
import com.carlosdmg.ecoclinic.feature.appointment.data.local.AppointmentDbLocalDataSource
import com.carlosdmg.ecoclinic.feature.appointment.data.remote.AppointmentsFirestoreRemoteDataSource
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.carlosdmg.ecoclinic.feature.appointment.domain.AppointmentsRepository
import org.koin.core.annotation.Single

@Single
class AppointmentsDataRepository(
    private val remoteDataSource: AppointmentsFirestoreRemoteDataSource,
    private val localDataSource: AppointmentDbLocalDataSource
) :
    AppointmentsRepository {

    override suspend fun getAppointments(
        patientId: String?
    ): Result<List<Appointment>> {

        localDataSource.deleteAll()

        return localDataSource.getAppointments().fold(

            onSuccess = { appointments ->
                Result.success(appointments)
            },

            onFailure = { error ->
                when (error) {
                    is ErrorApp.CacheExpiredErrorApp, is ErrorApp.NoDataError -> {
                        remoteDataSource.getAppointments(patientId).fold(
                            onSuccess = { appointments ->
                                localDataSource.saveAppointments(appointments)
                                Result.success(appointments)
                            },
                            onFailure = {
                                Result.failure(it)
                            }
                        )
                    }

                    else -> {
                        Result.failure(error)
                    }
                }
            }
        )
    }
}