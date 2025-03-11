package com.itami.workout_flow.workouts.presentation.screens.workouts

import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort

sealed interface WorkoutsAction {

    data class SearchQueryChange(val newValue: String) : WorkoutsAction

    data object OpenSearchClick : WorkoutsAction

    data object CloseSearchClick : WorkoutsAction

    data class ChangeWorkoutsSort(val workoutSort: WorkoutsSort) : WorkoutsAction

    data class ChangeWorkoutsFilter(val workoutsFilter: WorkoutsFilter) : WorkoutsAction

    data class WorkoutClick(val workoutId: String) : WorkoutsAction

    data object CreateWorkoutFABClick : WorkoutsAction

    data object SortClick : WorkoutsAction

    data object FilterClick : WorkoutsAction

    data object HideBottomSheet : WorkoutsAction

}

