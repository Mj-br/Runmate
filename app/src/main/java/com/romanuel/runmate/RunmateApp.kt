package com.romanuel.runmate

import android.app.Application
import com.romanuel.auth.data.di.authDataModule
import com.romanuel.auth.presentation.di.authViewModelModule
import com.romanuel.core.data.di.coreDataModule
import com.romanuel.run.location.di.locationModule
import com.romanuel.run.presentation.di.runPresentationModule
import com.romanuel.runmate.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class RunmateApp : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@RunmateApp)
            modules(
                appModule,
                authDataModule,
                authViewModelModule,
                coreDataModule,
                runPresentationModule,
                locationModule
            )
        }
    }
}
