package com.itami.workout_flow.workouts.presentation.model

import com.itami.workout_flow.model.Exercise

data class WorkoutExerciseUI(
    val id: String,
    val supersetId: String?,
    val exercise: Exercise,
    val sets: List<SetUI>,
    val order: Int,
)