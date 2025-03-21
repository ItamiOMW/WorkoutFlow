package com.itami.workout_flow.workouts.presentation.screens.search_exercise

sealed interface SearchExerciseAction {

    data object NavigateBack : SearchExerciseAction

}