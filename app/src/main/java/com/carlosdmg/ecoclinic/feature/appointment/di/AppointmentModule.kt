package com.carlosdmg.ecoclinic.feature.appointment.di

import com.carlosdmg.ecoclinic.app.data.db.EcoclinicDataBase
import com.carlosdmg.ecoclinic.feature.appointment.data.local.AppointmentsDao
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.carlosdmg.ecoclinic.feature.appointment")
class AppointmentModule {


    @Single
    fun provideAppointmentsDao(db: EcoclinicDataBase): AppointmentsDao {
        return db.appointmentsDao()
    }

}