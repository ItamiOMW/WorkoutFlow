package com.itami.workout_flow.onboarding.presentation.onboarding

sealed interface OnboardingEvent {

    data object NavigateToHomeScreen : OnboardingEvent

}