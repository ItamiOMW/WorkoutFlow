package com.itami.workout_flow.model

data class Exercise(
    val id: Long,
    val name: String,
    val description: String?,
    val exerciseGifUrl: String?,
    val exerciseType: ExerciseType,
    val steps: List<ExerciseStep>,
    val muscleInvolvements: List<MuscleInvolvement>,
    val equipments: List<Equipment>,
)