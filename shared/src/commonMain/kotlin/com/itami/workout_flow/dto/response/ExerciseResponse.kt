package com.itami.workout_flow.dto.response

import com.itami.workout_flow.model.Equipment
import com.itami.workout_flow.model.ExerciseType
import com.itami.workout_flow.model.Muscle
import com.itami.workout_flow.model.MuscleRole
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponse(
    val id: Long,
    val name: String,
    val exerciseGifUrl: String?,
    val exerciseType: ExerciseType,
    val steps: List<ExerciseStepResponse>,
    val muscleInvolvements: List<MuscleInvolvementResponse>,
    val equipments: List<Equipment>,
)

@Serializable
data class ExerciseStepResponse(
    val exerciseId: Long,
    val stepText: String,
    val order: Int,
)

@Serializable
data class MuscleInvolvementResponse(
    val exerciseId: Long,
    val muscle: Muscle,
    val muscleRole: MuscleRole,
)