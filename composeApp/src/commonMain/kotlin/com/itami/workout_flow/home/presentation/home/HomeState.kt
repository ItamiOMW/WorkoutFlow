package com.itami.workout_flow.home.presentation.home

import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout

data class HomeState(
    val scheduledWorkouts: List<ScheduledWorkout> = emptyList(),

    )
