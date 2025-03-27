package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import com.itami.workout_flow.model.Exercise

sealed interface SearchExerciseAction {

    data class SearchQueryChange(val newValue: String) : SearchExerciseAction

    data object OpenSearch : SearchExerciseAction

    data object CloseSearch : SearchExerciseAction

    data object OpenFilterSheet : SearchExerciseAction

    data object CloseFilterSheet : SearchExerciseAction

    data class ExerciseClick(val exercise: Exercise) : SearchExerciseAction

    data class ExerciseGifClick(val exercise: Exercise) : SearchExerciseAction

    data class ErrorOccurred(val errorMessage: String) : SearchExerciseAction

    data object NavigateBack : SearchExerciseAction

}