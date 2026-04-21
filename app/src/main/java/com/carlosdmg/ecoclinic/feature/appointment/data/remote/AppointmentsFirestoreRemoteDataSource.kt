package com.carlosdmg.ecoclinic.feature.appointment.data.remote

import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class AppointmentsFirestoreRemoteDataSource {

    val db = Firebase.firestore

    suspend fun getAppointments(patientId: String): Result<List<Appointment>> {
        val appointments = db.collection("appointments")
            .whereEqualTo("patientId", patientId)
            .get()
            .await()
            .map {
                it.toObject(AppointmentDbModel::class.java).toModel()
            }

        return Result.success(appointments)
    }

}