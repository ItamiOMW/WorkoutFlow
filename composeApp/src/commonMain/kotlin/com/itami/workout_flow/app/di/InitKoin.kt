package com.itami.workout_flow.app.di

import com.itami.workout_flow.onboarding.di.onboardingModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            platformModule,
            onboardingModule,
        )
    }
}