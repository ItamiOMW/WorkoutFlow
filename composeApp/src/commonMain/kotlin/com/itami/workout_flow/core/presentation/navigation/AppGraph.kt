package com.itami.workout_flow.core.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppGraph {

    @Serializable
    data object Onboarding : AppGraph {
        @Serializable
        data object OnboardingScreen
    }

    @Serializable
    data object Home : AppGraph {
        @Serializable
        data object HomeScreen
    }

}