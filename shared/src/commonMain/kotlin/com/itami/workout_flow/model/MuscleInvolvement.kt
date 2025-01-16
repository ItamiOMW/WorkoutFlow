package com.itami.workout_flow.model

data class MuscleInvolvement(
    val exerciseId: Long,
    val muscle: Muscle,
    val muscleRole: MuscleRole,
)