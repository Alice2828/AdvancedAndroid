package com.example.mvvm

import android.app.Application
import com.example.mvvm.di.*
import com.example.mvvm.di.sharedPrefModule
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
                    databaseModule,
                    networkModule,
                    sharedPrefModule,
                    viewModelModule
                )
            )
//            koin.loadModules(
//                listOf(
//                    repositoryModule,
//                    databaseModule,
//                    networkModule,
//                    useCaseModule,
//                    sharedPrefModule,
//                    viewModelModule
//                )
//            )
            koin.createRootScope()
        }
    }
}
