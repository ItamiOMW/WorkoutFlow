package com.itami.workout_flow.home.presentation.home.components

import com.itami.workout_flow.core.domain.model.workout.ScheduledWorkout
import kotlinx.datetime.LocalDate

data class RoutineDayUI(
    val date: LocalDate,
    val scheduledWorkouts: List<ScheduledWorkout>,
)