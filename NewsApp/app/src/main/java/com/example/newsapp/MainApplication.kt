package com.example.newsapp

import android.app.Application
import com.example.newsapp.di.networkModule
import com.example.newsapp.di.repositoryModule
import com.example.newsapp.di.sharedPrefModule
import com.example.newsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            koin.loadModules(listOf(repositoryModule,networkModule, sharedPrefModule, viewModelModule))
            koin.createRootScope()
            // modules(repositoryModule,networkModule, sharedPrefModule, viewModelModule)
        }
    }
}
