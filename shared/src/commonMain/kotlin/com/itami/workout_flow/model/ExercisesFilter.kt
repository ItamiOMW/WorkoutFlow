package com.itami.workout_flow.model

data class ExercisesFilter(
    val selectedMuscles: List<Muscle> = emptyList(),
    val selectedEquipments: List<Equipment> = emptyList(),
    val selectedExerciseTypes: List<ExerciseType> = emptyList(),
)