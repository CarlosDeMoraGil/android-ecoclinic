package com.carlosdmg.ecoclinic.app

import android.app.Application
import com.carlosdmg.ecoclinic.app.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class EcoclinicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@EcoclinicApplication)
            modules(
                AppModule().module
            )

        }
    }
}