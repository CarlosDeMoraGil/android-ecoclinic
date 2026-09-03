package com.carlosdmg.ecoclinic.feature.appointment.data.local

import com.carlosdmg.ecoclinic.app.domain.ErrorApp
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import org.koin.core.annotation.Single

@Single
class AppointmentDbLocalDataSource(private val dao: AppointmentsDao) {

    private val CACHE_VALID_MS: Long = 300_000L

    suspend fun getAppointments(): Result<List<Appointment>> {
        val appointments = dao.findAll()

        return when {
            appointments.isEmpty() -> {
                Result.failure(ErrorApp.NoDataError)
            }

            isCacheExpired(appointments.first().updatedAt) -> {
                Result.failure(ErrorApp.CacheExpiredErrorApp)
            }

            else -> {
                Result.success(appointments.map { it.toModel() })
            }
        }
    }

    private fun isCacheExpired(lastUpdated: Long): Boolean {
        return System.currentTimeMillis() - lastUpdated > CACHE_VALID_MS
    }

    suspend fun saveAppointments(appointments: List<Appointment>) {
        val currentTimeMilis = System.currentTimeMillis()
        val entities = appointments.map { it.toEntity(currentTimeMilis) }

        dao.saveAll(*entities.toTypedArray())
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }


}