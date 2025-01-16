package com.itami.workout_flow.app.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppGraph {

    @Serializable
    data object Home : AppGraph {
        @Serializable
        data object HomeScreen
    }

}