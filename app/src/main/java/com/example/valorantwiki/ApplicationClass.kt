package com.example.valorantwiki

import android.app.Application
import com.example.valorantwiki.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ApplicationClass)

            modules(
                mainModule,
                agentsFragmentModule,
                mapsFragmentModule,
                agentActivityModule,
                mapActivityModule,
                weaponsFragmentModule,
                weaponActivityModule
            )
        }
    }
}