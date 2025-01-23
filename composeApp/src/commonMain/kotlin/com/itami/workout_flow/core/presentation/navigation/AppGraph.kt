package com.itami.workout_flow.core.presentation.navigation

import com.itami.workout_flow.model.WorkoutType
import com.itami.workout_flow.model.WorkoutsSort
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

    @Serializable
    data object Workouts : AppGraph {
        @Serializable
        data class WorkoutsScreen(
            val launchMode: String = WorkoutsLaunchMode.Default.name,
            val workoutType: String? = null,
            val workoutSort: String = WorkoutsSort.Newest.name,
        ) {
            enum class WorkoutsLaunchMode {
                Default,
                Search,
                Favorites
            }
        }
    }

    @Serializable
    data object Progress : AppGraph {
        @Serializable
        data object ProgressScreen
    }

    @Serializable
    data object Profile : AppGraph {
        @Serializable
        data object CurrentUserProfileScreen
    }

}