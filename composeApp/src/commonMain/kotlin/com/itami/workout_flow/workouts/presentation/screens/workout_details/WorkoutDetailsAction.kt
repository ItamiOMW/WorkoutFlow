package com.itami.workout_flow.workouts.presentation.screens.workout_details

sealed interface WorkoutDetailsAction {

    data object NavigateBackClick : WorkoutDetailsAction

    data object FavoriteClick : WorkoutDetailsAction

    data object MoreOptionsClick : WorkoutDetailsAction

    data object DismissDropdownRequest : WorkoutDetailsAction

    data object StartWorkoutFABClick : WorkoutDetailsAction

    data object AuthorProfileImageClick : WorkoutDetailsAction

    data object StartWorkoutDropdownItemClick : WorkoutDetailsAction

    data object EditWorkoutDropdownItemClick : WorkoutDetailsAction

    data object AuthorProfileDropdownItemClick : WorkoutDetailsAction

    data class WorkoutExerciseExpandedStateChange(
        val workoutExerciseId: String,
        val expanded: Boolean
    ): WorkoutDetailsAction


}