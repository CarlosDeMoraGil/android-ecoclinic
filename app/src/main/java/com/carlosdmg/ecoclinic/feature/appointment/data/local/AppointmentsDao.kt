package com.carlosdmg.ecoclinic.feature.appointment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppointmentsDao {

    @Query("SELECT * FROM $APPOINTMENTS_TABLE")
    suspend fun findAll(): List<AppointmentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(vararg appointment: AppointmentEntity)

    @Query("DELETE FROM $APPOINTMENTS_TABLE")
    suspend fun deleteAll()
}