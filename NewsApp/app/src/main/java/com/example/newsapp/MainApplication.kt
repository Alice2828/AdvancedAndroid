package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            koin.loadModules(
                listOf(
                    repositoryModule,
                    useCaseModule,
                    networkModule,
                    sharedPrefModule,
                    viewModelModule
                )
            )
            koin.createRootScope()
        }
    }
}
