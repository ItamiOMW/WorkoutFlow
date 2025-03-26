package com.itami.workout_flow.workouts.presentation.screens.workout_editor

sealed interface WorkoutEditorEvent {

    data class ShowSnackbar(val message: String) : WorkoutEditorEvent

    data object NavigateToSearchExercise : WorkoutEditorEvent

    data object NavigateBack : WorkoutEditorEvent

}