package com.carlosdmg.ecoclinic.app

import android.app.Application
import com.carlosdmg.ecoclinic.app.di.AppModule
import com.carlosdmg.ecoclinic.feature.appointment.di.AppointmentModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class EcoclinicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        startKoin {
            androidContext(this@EcoclinicApplication)
            modules(
                AppModule().module,
                AppointmentModule().module
            )

        }
    }
}