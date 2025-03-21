package com.itami.workout_flow.workouts.presentation.screens.search_exercise

sealed interface SearchExerciseEvent {

    data object NavigateBack : SearchExerciseEvent

    data class ShowLocalSnackbar(val message: String) : SearchExerciseEvent

}