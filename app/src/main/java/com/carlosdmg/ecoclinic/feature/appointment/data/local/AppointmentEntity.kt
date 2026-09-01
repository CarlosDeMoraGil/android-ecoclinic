package com.carlosdmg.ecoclinic.feature.appointment.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val APPOINTMENTS_TABLE = "appointmentsTable"
const val APPOINTMENTS_ID = "id"

@Entity(tableName = APPOINTMENTS_TABLE)
class AppointmentEntity(
    @PrimaryKey @ColumnInfo(name = APPOINTMENTS_ID) val id: String,
    @ColumnInfo(name = "details") val details: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "patientId") val patientId: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "updatedAt") val updatedAt: Long,

    )