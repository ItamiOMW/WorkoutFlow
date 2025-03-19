package com.itami.workout_flow.workouts.presentation.model

data class SetUI(
    val id: String,
    val workoutExerciseId: String,
    val reps: Int?,
    val weightGrams: Float?,
    val distanceMeters: Float?,
    val hours: Int?,
    val minutes: Int?,
    val seconds: Int?,
    val order: Int,
)