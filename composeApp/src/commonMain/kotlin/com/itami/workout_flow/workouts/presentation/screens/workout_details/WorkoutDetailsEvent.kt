package com.itami.workout_flow.workouts.presentation.screens.workout_details

sealed interface WorkoutDetailsEvent {

    data object NavigateBack : WorkoutDetailsEvent

    data class NavigateToUserProfile(val userId: Long) : WorkoutDetailsEvent

    data class NavigateToEditWorkout(val workoutId: String) : WorkoutDetailsEvent


}