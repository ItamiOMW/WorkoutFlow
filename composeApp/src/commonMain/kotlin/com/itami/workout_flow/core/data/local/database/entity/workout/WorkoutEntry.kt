package com.itami.workout_flow.core.data.local.database.entity.workout

data class WorkoutEntry(
    val workout: WorkoutEntity,
    val workoutTypes: List<WorkoutTypeEntity>,
    val workoutMuscles: List<WorkoutMuscleEntity>,
    val workoutEquipments: List<WorkoutEquipmentEntity>,
    val workoutExerciseEntries: List<WorkoutExerciseEntry>,
)