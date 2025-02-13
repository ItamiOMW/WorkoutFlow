package com.itami.workout_flow.auth.di

import com.itami.workout_flow.BuildKonfig
import com.itami.workout_flow.auth.data.remote.AuthApiService
import com.itami.workout_flow.auth.data.remote.KtorAuthApiService
import com.itami.workout_flow.auth.data.repository.DefaultAuthRepository
import com.itami.workout_flow.auth.domain.repository.AuthRepository
import org.koin.dsl.module
import com.itami.workout_flow.auth.presentation.sign_in.SignInViewModel
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

val authModule = module {
    viewModelOf(::SignInViewModel)
    singleOf(::KtorAuthApiService).bind<AuthApiService>()
    singleOf(::DefaultAuthRepository).bind<AuthRepository>()
    single<GoogleAuthProvider> {
        GoogleAuthProvider.create(
            credentials = GoogleAuthCredentials(serverId = BuildKonfig.GOOGLE_WEB_CLIENT_ID)
        )
    }
}