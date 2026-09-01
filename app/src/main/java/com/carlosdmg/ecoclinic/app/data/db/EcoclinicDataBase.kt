package com.carlosdmg.ecoclinic.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.carlosdmg.ecoclinic.BuildConfig
import com.carlosdmg.ecoclinic.feature.appointment.data.local.AppointmentEntity
import com.carlosdmg.ecoclinic.feature.appointment.data.local.AppointmentsDao


@Database(
    entities = [AppointmentEntity::class],
    version = BuildConfig.VERSION_CODE,
    exportSchema = false
)
@TypeConverters()
abstract class EcoclinicDataBase : RoomDatabase() {

    abstract fun appointmentsDao(): AppointmentsDao

}
