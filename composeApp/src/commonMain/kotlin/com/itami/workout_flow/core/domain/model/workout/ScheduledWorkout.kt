package com.itami.workout_flow.core.domain.model.workout

import com.itami.workout_flow.model.DayOfWeek

data class ScheduledWorkout(
    val id: String,
    val workoutPreview: WorkoutPreview,
    val dayOfWeek: DayOfWeek,
)