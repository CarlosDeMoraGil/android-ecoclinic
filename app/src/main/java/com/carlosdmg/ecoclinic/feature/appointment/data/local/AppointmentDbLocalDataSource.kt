package com.carlosdmg.ecoclinic.feature.appointment.data.local

import com.carlosdmg.ecoclinic.app.domain.ErrorApp
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import org.koin.core.annotation.Single

@Single
class AppointmentDbLocalDataSource(private val dao: AppointmentsDao) {

    private val CACHE_VALID_MS: Long = 300_000L

    suspend fun getAppointments(): Result<List<Appointment>> {
        val appointments = dao.findAll()
        val lastUpdated = appointments.firstOrNull()?.updatedAt

        return when {
            appointments.isNotEmpty() -> Result.success(appointments.map { it.toModel() })
            lastUpdated != null && isCacheExpired(lastUpdated) -> Result.failure(ErrorApp.CacheExpiredErrorApp)
            else -> Result.failure(ErrorApp.UnknownErrorApp)
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


}