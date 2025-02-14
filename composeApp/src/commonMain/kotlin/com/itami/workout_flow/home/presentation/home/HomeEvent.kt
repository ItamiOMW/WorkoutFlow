package com.itami.workout_flow.home.presentation.home

import com.itami.workout_flow.core.presentation.utils.UiText
import com.itami.workout_flow.model.WorkoutType

sealed interface HomeEvent {

    data class ShowSnackbar(val message: UiText) : HomeEvent

    data object NavigateToRoutine : HomeEvent

    data object NavigateToSignIn : HomeEvent

    data object NavigateToProfile : HomeEvent

    data object NavigateToSearchWorkout : HomeEvent

    data class NavigateToSearchWorkoutByType(
        val workoutType: WorkoutType
    ) : HomeEvent

    data class NavigateToWorkoutDetails(val workoutId: String) : HomeEvent

}