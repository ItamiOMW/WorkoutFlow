package com.itami.workout_flow.core.data.local.database.entity.workout

data class WorkoutExerciseEntry(
    val workoutExercise: WorkoutExerciseEntity,
    val superset: SupersetEntity?,
    val sets: List<SetEntity>,
)