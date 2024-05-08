package com.romanuel.runmate

import android.app.Application
import com.romanuel.auth.data.di.authDataModule
import com.romanuel.auth.presentation.di.authViewModelModule
import com.romanuel.runmate.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunmateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin{
            androidLogger()
            androidContext(this@RunmateApp)
            modules(
                authDataModule,
                authViewModelModule,
                appModule
            )
        }
    }
}
