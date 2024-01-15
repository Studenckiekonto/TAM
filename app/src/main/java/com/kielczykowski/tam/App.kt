package com.kielczykowski.tam

import android.app.Application
import android.util.Log
import com.kielczykowski.tam.module.cityModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("App", "Uruchomienie Aplikacji")

        startKoin {
            androidContext(this@App)
            modules(cityModule)
        }
    }
}