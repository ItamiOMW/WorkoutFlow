package com.itami.workout_flow.auth.di

import com.itami.workout_flow.BuildKonfig
import org.koin.dsl.module
import com.itami.workout_flow.auth.presentation.sign_in.SignInViewModel
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.koin.core.module.dsl.viewModelOf

val authModule = module {
    viewModelOf(::SignInViewModel)
    single<GoogleAuthProvider> {
        GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(serverId = BuildKonfig.GOOGLE_WEB_CLIENT_ID)
        )
    }
}