package com.carlosdmg.ecoclinic.app.data.db

import android.content.Context
import androidx.room.Room
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class LocalModule {
    @Single
    fun provideDataBase(context: Context): EcoclinicDataBase {
        val db = Room.databaseBuilder(
            context,
            EcoclinicDataBase::class.java,
            "EcoClinic-db"
        )
        db.fallbackToDestructiveMigration(false)
        return db.build()
    }
}