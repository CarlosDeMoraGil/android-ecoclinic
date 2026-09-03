package com.carlosdmg.ecoclinic.feature.appointment.data.local

import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment

fun AppointmentEntity.toModel(): Appointment {
    return Appointment(this.id, this.details, this.timestamp, this.patientId, this.type)
}

fun Appointment.toEntity(dataDate: Long): AppointmentEntity {
    return AppointmentEntity(
        this.id,
        this.details,
        this.timestamp,
        this.patientId,
        this.type,
        dataDate
    )
}