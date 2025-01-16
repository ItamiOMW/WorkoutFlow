package com.itami.workout_flow.core.domain.model.workout

data class Set(
    val id: String,
    val workoutExerciseId: String,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val timeSeconds: Int?,
    val order: Int,
)