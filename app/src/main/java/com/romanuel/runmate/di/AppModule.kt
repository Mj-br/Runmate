package com.romanuel.runmate.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.romanuel.runmate.MainViewModel
import com.romanuel.runmate.RunmateApp
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

private const val SHARED_PREF_NAME = "auth_pref"

val appModule = module {
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            context = androidApplication(),
            fileName = SHARED_PREF_NAME,
            masterKey = MasterKey(androidApplication()),
            prefKeyEncryptionScheme = EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            prefValueEncryptionScheme = EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    single<CoroutineScope> {
        (androidApplication() as RunmateApp).applicationScope
    }

    viewModelOf(::MainViewModel)
}
