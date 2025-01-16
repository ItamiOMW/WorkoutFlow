package com.itami.workout_flow.core.domain.model.workout

data class WorkoutExercise(
    val id: String,
    val workoutId: String,
    val supersetId: String?,
    val exercise: Exercise,
    val sets: List<Set>,
    val order: Int,
)
