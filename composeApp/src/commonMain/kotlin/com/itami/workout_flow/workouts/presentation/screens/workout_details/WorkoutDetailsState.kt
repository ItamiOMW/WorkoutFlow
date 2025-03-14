package com.itami.workout_flow.workouts.presentation.screens.workout_details

import com.itami.workout_flow.core.domain.model.workout.DistanceUnit
import com.itami.workout_flow.core.domain.model.workout.WeightUnit
import com.itami.workout_flow.workouts.presentation.model.WorkoutUI

sealed interface WorkoutDetailsState {

    data object Initializing : WorkoutDetailsState

    data class Workout(
        val workoutUI: WorkoutUI,
        val weightUnit: WeightUnit,
        val distanceUnit: DistanceUnit,
        val dropdownExpanded: Boolean = false,
    ) : WorkoutDetailsState

    data object Empty : WorkoutDetailsState

}