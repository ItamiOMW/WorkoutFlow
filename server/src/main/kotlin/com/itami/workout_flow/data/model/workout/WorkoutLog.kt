package com.itami.workout_flow.data.model.workout

data class WorkoutLog(
    val serverId: Long,
    val clientUUID: String,
    val workoutServerId: Long?,
    val workoutClientUUID: String?,
    val isCompleted: Boolean,
)