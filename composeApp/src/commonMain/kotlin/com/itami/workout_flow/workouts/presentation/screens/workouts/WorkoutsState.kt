package com.itami.workout_flow.workouts.presentation.screens.workouts

import com.itami.workout_flow.model.WorkoutsFilter
import com.itami.workout_flow.model.WorkoutsSort
import org.jetbrains.compose.resources.StringResource
import workoutflow.composeapp.generated.resources.Res
import workoutflow.composeapp.generated.resources.custom
import workoutflow.composeapp.generated.resources.favorites
import workoutflow.composeapp.generated.resources.search

data class WorkoutsState(
    val searchQuery: String = "",
    val showSearchQuery: Boolean = false,
    val workoutSort: WorkoutsSort = WorkoutsSort.Newest,
    val workoutsFilter: WorkoutsFilter = WorkoutsFilter(),
    val workoutsBottomSheetContent: WorkoutsBottomSheetContent? = null,
) {
    enum class WorkoutsBottomSheetContent {
        SORT,
        FILTER;
    }

    enum class WorkoutsPagerScreen(val titleResId: StringResource) {
        CUSTOM(Res.string.custom),
        FAVORITES(Res.string.favorites),
        SEARCH(Res.string.search)
    }
}
