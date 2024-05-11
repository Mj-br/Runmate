package com.romanuel.auth.presentation.di

import com.romanuel.auth.presentation.login.LoginViewModel
import com.romanuel.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}
