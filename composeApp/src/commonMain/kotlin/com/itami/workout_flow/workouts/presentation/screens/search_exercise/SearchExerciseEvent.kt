package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import com.itami.workout_flow.model.Exercise

sealed interface SearchExerciseEvent {

    data object NavigateBack : SearchExerciseEvent

    data class NavigateBackWithResult(val exercise: Exercise) : SearchExerciseEvent

    data class NavigateToExerciseDetails(val exerciseId: Long) : SearchExerciseEvent

    data class ShowLocalSnackbar(val message: String) : SearchExerciseEvent

}