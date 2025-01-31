package com.itami.workout_flow.auth.di

import org.koin.dsl.module
import com.itami.workout_flow.auth.presentation.sign_in.SignInViewModel
import org.koin.core.module.dsl.viewModelOf

val authModule = module {
    viewModelOf(::SignInViewModel)
}