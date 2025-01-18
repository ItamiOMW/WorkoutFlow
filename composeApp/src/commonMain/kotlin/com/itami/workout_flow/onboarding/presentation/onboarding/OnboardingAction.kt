package com.itami.workout_flow.onboarding.presentation.onboarding

sealed interface OnboardingAction {

    data object NextClick : OnboardingAction

}