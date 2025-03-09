package com.itami.workout_flow.model

data class WorkoutsFilter(
    val selectedMuscles: List<Muscle> = emptyList(),
    val selectedEquipments: List<Equipment> = emptyList(),
    val selectedWorkoutTypes: List<WorkoutType> = emptyList(),
    val selectedTimeFilters: List<WorkoutTimeFilter> = emptyList(),
)