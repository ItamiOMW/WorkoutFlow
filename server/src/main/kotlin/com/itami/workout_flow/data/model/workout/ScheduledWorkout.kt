package com.itami.workout_flow.data.model.workout

import com.itami.workout_flow.model.DayOfWeek

data class ScheduledWorkout(
    val serverId: Long,
    val clientUUID: String,
    val workout: Workout,
    val dayOfWeek: DayOfWeek,
)