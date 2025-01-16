package com.itami.workout_flow.data.model.workout

data class SetLog(
    val serverId: Long,
    val clientUUID: String,
    val workoutExerciseLogServerId: Long,
    val workoutExerciseLogClientUUID: String,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val timeMillis: Long?,
    val order: Int,
)