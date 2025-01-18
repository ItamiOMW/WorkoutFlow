package com.itami.workout_flow.onboarding.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import com.itami.workout_flow.onboarding.presentation.onboarding.OnboardingViewModel

val onboardingModule = module {
    viewModelOf(::OnboardingViewModel)
}