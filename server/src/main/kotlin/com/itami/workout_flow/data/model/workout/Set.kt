package com.itami.workout_flow.data.model.workout

data class Set(
    val serverId: Long,
    val clientUUID: String,
    val workoutExerciseId: Long,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val timeSeconds: Int?,
    val order: Int,
)