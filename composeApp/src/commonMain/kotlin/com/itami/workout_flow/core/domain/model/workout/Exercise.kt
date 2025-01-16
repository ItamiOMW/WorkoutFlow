package com.itami.workout_flow.core.domain.model.workout

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.MuscleInvolvement

data class Exercise(
    val id: Long,
    val name: String,
    val description: String?,
    val exerciseGifUrl: String?,
    val exerciseType: ExerciseType,
    val steps: List<String>,
    val muscleInvolvements: List<MuscleInvolvement>,
    val equipments: List<Equipment>,
)
