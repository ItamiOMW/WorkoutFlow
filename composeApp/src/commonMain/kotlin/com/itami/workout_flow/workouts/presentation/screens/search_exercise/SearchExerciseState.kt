package com.itami.workout_flow.workouts.presentation.screens.search_exercise

import com.itami.workout_flow.core.presentation.navigation.AppGraph.Workouts.SearchExerciseScreen.SearchExerciseLaunchMode
import com.itami.workout_flow.model.ExercisesFilter

data class SearchExerciseState(
    val isLoading: Boolean = false,
    val launchMode: SearchExerciseLaunchMode = SearchExerciseLaunchMode.Search,
    val searchQuery: String = "",
    val showSearchQuery: Boolean = false,
    val exercisesFilter: ExercisesFilter = ExercisesFilter(),
    val showExerciseFilterSheet: Boolean = false,
)
