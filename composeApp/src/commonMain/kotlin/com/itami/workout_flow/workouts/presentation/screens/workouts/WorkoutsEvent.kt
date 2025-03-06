package com.itami.workout_flow.workouts.presentation.screens.workouts

sealed interface WorkoutsEvent {

    data class ShowLocalSnackbar(val message: String) : WorkoutsEvent

    data class NavigateToWorkoutDetails(val workoutId: String) : WorkoutsEvent

    data object NavigateToCreateWorkout : WorkoutsEvent

}