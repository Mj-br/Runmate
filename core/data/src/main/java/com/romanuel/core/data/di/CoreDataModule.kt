package com.romanuel.core.data.di

import com.romanuel.core.data.auth.EncryptedSessionStorage
import com.romanuel.core.data.networking.HttpClientFactory
import com.romanuel.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}
