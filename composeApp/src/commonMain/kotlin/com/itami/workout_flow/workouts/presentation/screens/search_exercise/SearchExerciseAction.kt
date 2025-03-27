package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import com.itami.workout_flow.model.Exercise

sealed interface SearchExerciseAction {

    data class SearchQueryChange(val newValue: String) : SearchExerciseAction

    data object ShowSearchQuery : SearchExerciseAction

    data object HideSearchQuery : SearchExerciseAction

    data object ShowFilterSheet : SearchExerciseAction

    data object HideFilterSheet : SearchExerciseAction

    data class ExerciseClick(val exercise: Exercise) : SearchExerciseAction

    data class ExerciseGifClick(val exercise: Exercise) : SearchExerciseAction

    data class ErrorOccurred(val errorMessage: String) : SearchExerciseAction

    data object NavigateBack : SearchExerciseAction

}